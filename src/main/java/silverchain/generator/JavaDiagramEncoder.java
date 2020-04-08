package silverchain.generator;

import static silverchain.generator.JavaASTEncoder.encode;
import static silverchain.generator.JavaASTEncoder.encodeAsArgument;
import static silverchain.generator.JavaASTEncoder.encodeAsDeclaration;
import static silverchain.generator.JavaASTEncoder.encodeAsInvocation;

import java.util.Optional;
import silverchain.diagram.Diagram;
import silverchain.diagram.Label;
import silverchain.diagram.State;
import silverchain.diagram.Transition;

final class JavaDiagramEncoder {

  private JavaDiagramEncoder() {}

  static String interfaceModifier(State state) {
    return state.number() == 0 ? "" : "public ";
  }

  static String interfaceQualifiedName(State state) {
    return qualifiedName(interfacePackageName(state), interfaceName(state));
  }

  static String interfacePackageName(State state) {
    String qualifier = state.diagram().name().qualifier().map(JavaASTEncoder::encode).orElse("");
    return state.number() == 0 ? qualifier : qualifiedName(qualifier, "state" + state.number());
  }

  static String interfaceName(State state) {
    return (state.number() == 0 ? "I" : "") + state.diagram().name().name();
  }

  static String implementationQualifiedName(State state) {
    return qualifiedName(implementationPackageName(state), implementationName(state));
  }

  static String implementationPackageName(State state) {
    return state.diagram().name().qualifier().map(JavaASTEncoder::encode).orElse("");
  }

  static String implementationName(State state) {
    return state.diagram().name().name() + state.number();
  }

  static String interfaceQualifiedName(Diagram diagram) {
    return qualifiedName(interfacePackageName(diagram), interfaceName(diagram));
  }

  static String interfacePackageName(Diagram diagram) {
    return diagram.name().qualifier().map(JavaASTEncoder::encode).orElse("");
  }

  static String interfaceName(Diagram diagram) {
    return "I" + diagram.name().name() + "Action";
  }

  static String stateMethodDeclaration(Transition t) {
    String s = interfaceReference(t.destination()) + " " + encodeAsDeclaration(t.label().method());
    return (t.typeParameters().isEmpty() ? "" : encodeAsDeclaration(t.typeParameters()) + " ") + s;
  }

  static String interfaceReference(State state) {
    if (state.isNumbered()) {
      return interfaceQualifiedName(state) + encodeAsArgument(state.typeParameters());
    }
    return typeReference(state).map(l -> encode(l.typeReference())).orElse("void");
  }

  static String stateMethodBodyListenerInvocation(Transition t) {
    String prefix = typeReference(t.destination()).map(r -> "return ").orElse("");
    return "    " + prefix + "this.action." + actionMethodInvocation(t, true) + "\n";
  }

  static String stateMethodBodyReturnState(Transition t) {
    if (t.destination().isNumbered()) {
      return "    return new " + implementationQualifiedName(t.destination()) + "(this.action);\n";
    }
    return "";
  }

  static String actionMethodDeclaration(Transition t, boolean withPrefix) {
    String type = t.destination().isNumbered() ? "void" : interfaceReference(t.destination());
    String prefix = withPrefix ? ("state" + t.source().number() + "$") : "";
    return type + " " + prefix + encodeAsDeclaration(t.label().method());
  }

  private static String actionMethodInvocation(Transition t, boolean withPrefix) {
    String prefix = withPrefix ? ("state" + t.source().number() + "$") : "";
    return prefix + encodeAsInvocation(t.label().method()) + ";";
  }

  static String actionMethodDefaultBody(Transition t) {
    String prefix = typeReference(t.destination()).map(r -> "return ").orElse("");
    return prefix + actionMethodInvocation(t, false);
  }

  private static Optional<Label> typeReference(State state) {
    return state.typeReferences().stream().findFirst();
  }

  private static String qualifiedName(String qualifier, String name) {
    return qualifier.isEmpty() ? name : qualifier + "." + name;
  }
}
