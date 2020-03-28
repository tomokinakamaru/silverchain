package silverchain.generator.java;

import static silverchain.generator.java.GrammarEncoder.encode;
import static silverchain.generator.java.Utility.qualifiedName;

import silverchain.generator.diagram.Diagram;

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
