package silverchain.codegen.java;

import silverchain.diagram.DiagramBuilder;

final class JavaDiagramBuilder extends DiagramBuilder<JavaDiagram, JavaState, JavaTransition> {

  JavaDiagramBuilder() {
    super(
        JavaDiagram::new,
        JavaState::new,
        JavaTransition::new,
        state -> state.typeReferences().isEmpty() && !state.isEnd());
  }
}
