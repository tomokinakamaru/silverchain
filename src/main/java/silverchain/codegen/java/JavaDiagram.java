package silverchain.codegen.java;

import static silverchain.codegen.java.GrammarEncoder.encode;
import static silverchain.codegen.java.Utility.qualifiedName;

import silverchain.diagram.Diagram;

final class JavaDiagram extends Diagram<JavaDiagram, JavaState, JavaTransition> {

  String actionInterfaceName() {
    return "I" + name().name() + "Action";
  }

  String actionInterfacePackageName() {
    return encode(name().qualifier());
  }

  String actionInterfaceQualifiedName() {
    return qualifiedName(actionInterfacePackageName(), actionInterfaceName());
  }
}
