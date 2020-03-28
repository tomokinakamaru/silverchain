package silverchain.generator.java;

import silverchain.generator.diagram.DiagramBuilder;

final class JavaDiagramBuilder extends DiagramBuilder<JavaDiagram, JavaState, JavaTransition> {

  JavaDiagramBuilder() {
    super(
        JavaDiagram::new,
        JavaState::new,
        JavaTransition::new,
        state -> state.typeReferences().isEmpty() && !state.isEnd());
  }
}
