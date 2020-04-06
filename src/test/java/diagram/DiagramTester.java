package diagram;

import silverchain.diagram.Diagram;
import silverchain.diagram.State;
import silverchain.diagram.Transition;

final class DiagramTester {

  private final Diagram diagram;

  DiagramTester(Diagram diagram) {
    this.diagram = diagram;

    for (State state : diagram.states()) {
      assert state.diagram() == diagram;
    }

    for (int i = 0; i < diagram.states().size(); i++) {
      assert state(i).isStart() == (i == 0);
    }

    for (State state : diagram.states()) {
      for (Transition transition : state.transitions()) {
        assert transition.source() == state;
      }
    }
  }

  DiagramTester name(String text) {
    assert diagram.name().toString().equals(text);
    return this;
  }

  DiagramTester typeParameterCount(int n) {
    assert diagram.typeParameters().size() == n;
    return this;
  }

  DiagramTester typeParameter(int i, String text) {
    assert diagram.typeParameters().get(i).toString().equals(text);
    return this;
  }

  DiagramTester stateCount(int n) {
    assert diagram.states().size() == n;
    return this;
  }

  DiagramTester end(int... is) {
    for (int i = 0; i < diagram.states().size(); i++) {
      assert state(i).isEnd() == in(is, i);
    }
    return this;
  }

  DiagramTester stateTypeParameterCount(int i, int n) {
    assert state(i).typeParameters().size() == n;
    return this;
  }

  DiagramTester stateTypeParameter(int i, int j, String text) {
    assert state(i).typeParameters().get(j).toString().equals(text);
    return this;
  }

  DiagramTester stateTypeReferenceCount(int i, int n) {
    assert state(i).typeReferences().size() == n;
    return this;
  }

  DiagramTester stateTypeReference(int i, int j, String text) {
    assert state(i).typeReferences().get(j).typeReference().toString().equals(text);
    return this;
  }

  DiagramTester transitionCount(int i, int n) {
    assert state(i).transitions().size() == n;
    return this;
  }

  DiagramTester transitionDestination(int i, int j, int k) {
    assert state(i).transitions().get(j).destination() == state(k);
    return this;
  }

  DiagramTester transitionTypeParameterCount(int i, int j, int n) {
    assert state(i).transitions().get(j).typeParameters().size() == n;
    return this;
  }

  DiagramTester transitionTypeParameter(int i, int j, int k, String text) {
    assert state(i).transitions().get(j).typeParameters().get(k).toString().equals(text);
    return this;
  }

  DiagramTester transitionLabelRangeCount(int i, int j, int n) {
    assert state(i).transitions().get(j).label().ranges().size() == n;
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
