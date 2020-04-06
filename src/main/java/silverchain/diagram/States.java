package silverchain.diagram;

import java.util.HashSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

final class States extends HashSet<State> {

  States() {}

  States(State state) {
    add(state);
  }

  States(States states1, States states2) {
    addAll(states1);
    addAll(states2);
  }

  static Collector<State, ?, States> collector() {
    return Collectors.toCollection(States::new);
  }
}
