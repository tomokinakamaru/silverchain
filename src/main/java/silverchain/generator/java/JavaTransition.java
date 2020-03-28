package silverchain.generator.java;

import static silverchain.generator.java.GrammarEncoder.encode;
import static silverchain.generator.java.GrammarEncoder.encodeAsDeclaration;
import static silverchain.generator.java.GrammarEncoder.encodeAsInvocation;

import java.util.ArrayList;
import java.util.List;
import silverchain.generator.diagram.Transition;
import silverchain.grammar.TypeParameter;

final class JavaTransition extends Transition<JavaDiagram, JavaState, JavaTransition> {

  String stateMethodDeclaration() {
    String decl = destination().reference() + " " + encodeAsDeclaration(method());
    if (parameters().isEmpty()) {
      return decl;
    }
    return encode(parameters()) + " " + decl;
  }

  String actionMethodDeclaration(boolean withPrefix) {
    String type = destination().isNumbered() ? "void" : destination().reference();
    String prefix = withPrefix ? ("state" + source().number() + "$") : "";
    return type + " " + prefix + encodeAsDeclaration(method());
  }

  String actionMethodInvocation(boolean withPrefix) {
    String prefix = withPrefix ? ("state" + source().number() + "$") : "";
    return prefix + encodeAsInvocation(method()) + ";";
  }

  String actionMethodDefaultBody() {
    String prefix = destination().typeReference().map(r -> "return ").orElse("");
    return prefix + actionMethodInvocation(false);
  }

  String stateMethodBodyListenerInvocation() {
    String type = destination().typeReference().map(r -> "return ").orElse("");
    return "    " + type + "this.action." + actionMethodInvocation(true) + "\n";
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
