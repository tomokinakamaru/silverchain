package diagram;

import static org.assertj.core.api.Assertions.assertThat;

import silverchain.diagram.Diagram;
import silverchain.diagram.State;
import silverchain.diagram.Transition;

final class DiagramTester {

  private final Diagram diagram;

  DiagramTester(Diagram diagram) {
    this.diagram = diagram;

    for (State state : diagram.states()) {
      assertThat(state.diagram()).isSameAs(diagram);
    }

    for (int i = 0; i < diagram.states().size(); i++) {
      assertThat(state(i).isStart()).isSameAs((i == 0));
    }

    for (State state : diagram.states()) {
      for (Transition transition : state.transitions()) {
        assertThat(transition.source()).isSameAs(state);
      }
    }
  }

  DiagramTester name(String text) {
    assertThat((Object) diagram.name()).hasToString(text);
    return this;
  }

  DiagramTester typeParameterCount(int n) {
    assertThat(diagram.typeParameters().size()).isSameAs(n);
    return this;
  }

  DiagramTester typeParameter(int i, String text) {
    assertThat(diagram.typeParameters().get(i)).hasToString(text);
    return this;
  }

  DiagramTester stateCount(int n) {
    assertThat(diagram.states().size()).isSameAs(n);
    return this;
  }

  DiagramTester end(int... is) {
    for (int i = 0; i < diagram.states().size(); i++) {
      assertThat(state(i).isEnd()).isSameAs(in(is, i));
    }
    return this;
  }

  DiagramTester stateTypeParameterCount(int i, int n) {
    assertThat(state(i).typeParameters().size()).isSameAs(n);
    return this;
  }

  DiagramTester stateTypeParameter(int i, int j, String text) {
    assertThat(state(i).typeParameters().get(j)).hasToString(text);
    return this;
  }

  DiagramTester stateTypeReferenceCount(int i, int n) {
    assertThat(state(i).typeReferences().size()).isSameAs(n);
    return this;
  }

  DiagramTester stateTypeReference(int i, int j, String text) {
    assertThat(state(i).typeReferences().get(j).typeReference()).hasToString(text);
    return this;
  }

  DiagramTester transitionCount(int i, int n) {
    assertThat(state(i).transitions().size()).isSameAs(n);
    return this;
  }

  DiagramTester transitionDestination(int i, int j, int k) {
    assertThat(state(i).transitions().get(j).destination()).isSameAs(state(k));
    return this;
  }

  DiagramTester transitionTypeParameterCount(int i, int j, int n) {
    assertThat(state(i).transitions().get(j).typeParameters().size()).isSameAs(n);
    return this;
  }

  DiagramTester transitionTypeParameter(int i, int j, int k, String text) {
    assertThat(state(i).transitions().get(j).typeParameters().get(k)).hasToString(text);
    return this;
  }

  DiagramTester transitionLabelRangeCount(int i, int j, int n) {
    assertThat(state(i).transitions().get(j).label().ranges().size()).isSameAs(n);
    return this;
  }

  private State state(int i) {
    return diagram.states().get(i);
  }

  private static boolean in(int[] is, int i) {
    for (int j : is) {
      if (j == i) {
        return true;
      }
    }
    return false;
  }
}
