package silverchain.diagram;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

final class Tracer<T> {

  private final Map<T, State> states = new HashMap<>();

  private final Transitions transitions = new Transitions();

  Tracer(T state) {
    trace(state);
  }

  Tracer(Collection<T> states) {
    states.forEach(this::trace);
  }

  void trace(T state) {
    states.computeIfAbsent(state, t -> new State());
  }

  void trace(T source, T destination, Label label) {
    trace(source);
    trace(destination);
    transitions.add(new Transition(states.get(source), states.get(destination), label));
  }

  States states(Predicate<T> predicate) {
    return states.keySet().stream().filter(predicate).map(states::get).collect(States.collector());
  }

  Transitions transitions() {
    return transitions;
  }
}
