package silverchain.diagram;

import static silverchain.diagram.Utility.toArrayList;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import silverchain.parser.TypeParameter;

public final class State {

  private static final int UNASSIGNED = -1;

  Set<TypeParameter> typeParameters;

  Diagram diagram;

  boolean isStart;

  boolean isEnd;

  List<Transition> transitions;

  int number = UNASSIGNED;

  State() {}

  public Diagram diagram() {
    return diagram;
  }

  public boolean isStart() {
    return isStart;
  }

  public boolean isEnd() {
    return isEnd || !typeReferences().isEmpty();
  }

  public List<TypeParameter> typeParameters() {
    Set<TypeParameter> parameters = new LinkedHashSet<>(typeParameters);
    for (Label label : typeReferences()) {
      parameters.addAll(label.typeReference().referents());
    }
    return new ArrayList<>(parameters);
  }

  public List<Label> typeReferences() {
    return transitions.stream()
        .filter(t -> t.label.isTypeReference())
        .map(t -> t.label)
        .collect(toArrayList());
  }

  public Optional<Label> typeReference() {
    return typeReferences().stream().findFirst();
  }

  public List<Transition> transitions() {
    return transitions.stream().filter(t -> t.label.isMethod()).collect(toArrayList());
  }

  public boolean isNumbered() {
    return number != UNASSIGNED;
  }

  public int number() {
    return number;
  }
}
