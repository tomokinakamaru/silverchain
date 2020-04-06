package silverchain.diagram;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import silverchain.parser.TypeParameter;

public final class Transition implements Comparable<Transition> {

  State source;

  State destination;

  final Label label;

  Transition(Label label) {
    this(new State(), new State(), label);
  }

  Transition(State source, State destination) {
    this(source, destination, null);
  }

  Transition(State source, State destination, Label label) {
    this.source = source;
    this.destination = destination;
    this.label = label;
  }

  void reverse() {
    State state = source;
    source = destination;
    destination = state;
  }

  Set<TypeParameter> destinationParameters() {
    Set<TypeParameter> parameters = new LinkedHashSet<>();
    parameters.addAll(source.typeParameters);
    parameters.addAll(label.parameters());
    return parameters;
  }

  public State source() {
    return source;
  }

  public State destination() {
    return destination;
  }

  public Label label() {
    return label;
  }

  public List<TypeParameter> typeParameters() {
    Set<TypeParameter> parameters = new LinkedHashSet<>(destination.typeParameters());
    parameters.removeAll(source.typeParameters());
    return new ArrayList<>(parameters);
  }

  @Override
  public int compareTo(Transition transition) {
    return label.compareTo(transition.label);
  }
}
