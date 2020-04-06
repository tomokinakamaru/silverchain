package silverchain.diagram;

import static silverchain.diagram.Utility.toArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

final class Transitions extends HashSet<Transition> {

  Transitions() {}

  Transitions(Transition transition) {
    add(transition);
  }

  Transitions(Transitions transitions1, Transitions transitions2) {
    addAll(transitions1);
    addAll(transitions2);
  }

  static Collector<Transition, ?, Transitions> collector() {
    return Collectors.toCollection(Transitions::new);
  }

  void fuse(States sources, States destinations) {
    sources.forEach(s -> destinations.forEach(d -> add(new Transition(s, d))));
  }

  void reverse() {
    forEach(Transition::reverse);
  }

  States destinations(States sources, Label label) {
    return stream()
        .filter(t -> sources.contains(t.source))
        .filter(t -> Objects.equals(t.label, label))
        .map(t -> t.destination)
        .collect(States.collector());
  }

  Labels labels(States sources) {
    Labels labels = new Labels();
    for (Transition t : this) {
      if (sources.contains(t.source)) {
        labels.add(t.label);
      }
    }
    return labels;
  }

  Transitions from(State state) {
    return stream().filter(t -> t.source == state).collect(collector());
  }

  List<Transition> fromSorted(State state) {
    return stream().filter(t -> t.source == state).sorted().collect(toArrayList());
  }
}
