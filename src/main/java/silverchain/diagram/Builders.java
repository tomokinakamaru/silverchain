package silverchain.diagram;

import silverchain.parser.ASTNode;

public final class Builders {

  private Builders() {}

  public static Diagram atom() {
    return atom((Label) null);
  }

  public static Diagram atom(ASTNode node) {
    return atom(new Label(node));
  }

  public static Diagram copy(Diagram diagram) {
    Tracer<State> tracer = new Tracer<>(diagram.startStates);
    diagram.transitions.forEach(t -> tracer.trace(t.source, t.destination, t.label));
    return new Diagram(
        tracer.states(diagram.startStates::contains),
        tracer.states(diagram.endStates::contains),
        tracer.transitions());
  }

  public static Diagram repeat(Diagram diagram) {
    diagram = copy(diagram);
    diagram.transitions.fuse(diagram.startStates, diagram.endStates);
    diagram.transitions.fuse(diagram.endStates, diagram.startStates);
    return diagram;
  }

  public static Diagram join(Diagram diagram1, Diagram diagram2) {
    diagram1 = copy(diagram1);
    Diagram diagram = new Diagram();
    diagram.startStates = diagram1.startStates;
    diagram.endStates = diagram2.endStates;
    diagram.transitions = new Transitions(diagram1.transitions, diagram2.transitions);
    diagram.transitions.fuse(diagram1.endStates, diagram2.startStates);
    return diagram;
  }

  public static Diagram merge(Diagram diagram1, Diagram diagram2) {
    diagram1 = copy(diagram1);
    return new Diagram(
        new States(diagram1.startStates, diagram2.startStates),
        new States(diagram1.endStates, diagram2.endStates),
        new Transitions(diagram1.transitions, diagram2.transitions));
  }

  private static Diagram atom(Label label) {
    Transition transition = new Transition(label);
    return new Diagram(
        new States(transition.source),
        new States(transition.destination),
        new Transitions(transition));
  }
}
