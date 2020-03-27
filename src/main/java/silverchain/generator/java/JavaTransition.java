package silverchain.generator.java;

import static silverchain.generator.java.GrammarEncoder.encode;
import static silverchain.generator.java.GrammarEncoder.encodeAsDeclaration;
import static silverchain.generator.java.GrammarEncoder.encodeAsInvocation;

import java.util.ArrayList;
import java.util.List;
import silverchain.diagram.Transition;
import silverchain.grammar.TypeParameter;

final class JavaTransition extends Transition<JavaDiagram, JavaState, JavaTransition> {

  String stateMethodDeclaration() {
    String decl = destination().reference() + " " + encodeAsDeclaration(method());
    if (parameters().isEmpty()) {
      return decl;
    }
    return encode(parameters()) + " " + decl;
  }

  String actionMethodDeclaration() {
    String type = destination().isNumbered() ? "void" : destination().reference();
    return type + " state" + source().number() + "$" + encodeAsDeclaration(method());
  }

  String actionMethodInvocation() {
    return ".state" + source().number() + "$" + encodeAsInvocation(method()) + ";";
  }

  String stateMethodBodyListenerInvocation() {
    String type = destination().typeReference().map(r -> "return ").orElse("");
    return "    " + type + "this.action" + actionMethodInvocation() + "\n";
  }

  String stateMethodBodyReturnState() {
    if (destination().isNumbered()) {
      String name = destination().implementationQualifiedName();
      return "    return new " + name + "(this.action);\n";
    }
    return "";
  }

  private List<TypeParameter> parameters() {
    List<TypeParameter> parameters = new ArrayList<>(destination().parameters());
    parameters.removeAll(source().parameters());
    return parameters;
  }
}
