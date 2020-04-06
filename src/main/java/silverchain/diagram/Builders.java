package silverchain.diagram;

import static java.util.stream.Stream.generate;

import silverchain.parser.ASTNode;

public final class Builders {

  private Builders() {}

  public static Diagram atom(ASTNode node) {
    return atom(new Transition(new Label(node)));
  }

  public static Diagram repeat(Diagram diagram, int min, Integer max) {
    Diagram first = generate(() -> diagram).limit(min).reduce(Builders::join).orElse(atom());
    Diagram second;
    if (max == null) {
      second = copy(diagram);
      second.transitions.fuse(second.startStates, second.endStates);
      second.transitions.fuse(second.endStates, second.startStates);
    } else {
      Diagram d = copy(diagram);
      d.transitions.fuse(d.startStates, d.endStates);
      second = generate(() -> d).limit(max - min).reduce(Builders::join).orElse(atom());
    }
    return join(first, second);
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

  private static Diagram copy(Diagram diagram) {
    Tracer<State> tracer = new Tracer<>(diagram.startStates);
    diagram.transitions.forEach(t -> tracer.trace(t.source, t.destination, t.label));
    return new Diagram(
        tracer.states(diagram.startStates::contains),
        tracer.states(diagram.endStates::contains),
        tracer.transitions());
  }

  private static Diagram atom() {
    return atom(new Transition(null));
  }

  private static Diagram atom(Transition t) {
    return new Diagram(new States(t.source), new States(t.destination), new Transitions(t));
  }
}
