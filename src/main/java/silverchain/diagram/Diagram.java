package silverchain.diagram;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import silverchain.parser.QualifiedName;
import silverchain.parser.Type;
import silverchain.parser.TypeParameter;

public class Diagram {

  States startStates;

  States endStates;

  Transitions transitions;

  Type type;

  List<State> sortedStates;

  Diagram() {}

  Diagram(States startStates, States endStates, Transitions transitions) {
    this.startStates = startStates;
    this.endStates = endStates;
    this.transitions = transitions;
  }

  public Diagram compile() {
    reverse();
    determinize();
    reverse();
    determinize();
    propagateTags();

    Traverser<State> traverser = new Traverser<>(startStates);
    while (traverser.hasNext()) {
      State s = traverser.next();
      s.diagram = this;
      s.transitions = transitions.fromSorted(s);
      s.isStart = secondState() == s;
      s.isEnd = endStates.contains(s);
      s.transitions.stream()
          .filter(t -> t.label.isMethod() || t.label.isType())
          .forEach(t -> traverser.enqueue(t.destination));
    }
    sortedStates = traverser.queued();
    sortedStates.removeAll(startStates);

    type = firstTransition().label.type();
    return this;
  }

  public void assignStateNumbers(Predicate<State> predicate) {
    int n = 0;
    for (State state : states()) {
      if (predicate.test(state)) {
        state.number = n;
        n++;
      }
    }
  }

  public QualifiedName name() {
    return type.name();
  }

  public List<TypeParameter> typeParameters() {
    return type.typeParameters();
  }

  public List<State> states() {
    return new ArrayList<>(sortedStates);
  }

  public List<State> numberedStates() {
    return sortedStates.stream()
        .filter(State::isNumbered)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private void reverse() {
    States states = startStates;
    startStates = endStates;
    endStates = states;
    transitions.reverse();
  }

  private void determinize() {
    States startState = epsilonClosure(startStates);
    Tracer<States> tracer = new Tracer<States>(startState);
    Traverser<States> traverser = new Traverser<States>(startState);
    while (traverser.hasNext()) {
      States src = traverser.next();
      for (Label label : transitions.labels(src)) {
        States dst = epsilonClosure(transitions.destinations(src, label));
        traverser.enqueue(dst);
        tracer.trace(src, dst, label);
      }
    }
    startStates = tracer.states(s -> s == startState);
    endStates = tracer.states(s -> endStates.stream().anyMatch(s::contains));
    transitions = tracer.transitions();
  }

  private void propagateTags() {
    State startState = startStates.iterator().next();
    startState.typeParameters = new LinkedHashSet<>();

    Traverser<State> traverser = new Traverser<>(startState);
    while (traverser.hasNext()) {
      for (Transition transition : transitions.from(traverser.next())) {
        traverser.enqueue(propagateTags(transition));
      }
    }
  }

  private State propagateTags(Transition transition) {
    Set<TypeParameter> typeParameters = transition.destinationParameters();
    if (transition.destination.typeParameters == null) {
      transition.destination.typeParameters = typeParameters;
      return transition.destination;
    }
    if (!transition.destination.typeParameters.equals(typeParameters)) {
      State state = cloneDestination(transition);
      state.typeParameters = typeParameters;
      transitions.remove(transition);
      transitions.add(new Transition(transition.source, state, transition.label));
      return state;
    }
    return null;
  }

  private State cloneDestination(Transition transition) {
    State original = transition.destination;
    State cloned = new State();
    if (endStates.contains(original)) {
      endStates.add(cloned);
    }
    for (Transition t : transitions.from(original)) {
      State d = t.source == t.destination ? cloned : t.destination;
      transitions.add(new Transition(cloned, d, t.label));
    }
    return cloned;
  }

  private States epsilonClosure(States states) {
    Traverser<State> traverser = new Traverser<>(states);
    while (traverser.hasNext()) {
      State state = traverser.next();
      traverser.enqueue(transitions.destinations(new States(state), null));
    }
    return traverser.queued().stream().collect(States.collector());
  }

  private State firstState() {
    return startStates.iterator().next();
  }

  private State secondState() {
    return firstTransition().destination;
  }

  private Transition firstTransition() {
    return transitions.from(firstState()).iterator().next();
  }
}
