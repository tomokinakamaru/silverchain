package silverchain.diagram;

import java.util.ArrayList;
import java.util.List;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeReference;

public abstract class State<
    D extends Diagram<D, S, T>, S extends State<D, S, T>, T extends Transition<D, S, T>> {

  private static final int UNASSIGNED = -1;

  boolean isEnd;

  int number = UNASSIGNED;

  D diagram;

  final List<TypeParameter> parameters = new ArrayList<>();

  final List<TypeReference> typeReferences = new ArrayList<>();

  final List<T> transitions = new ArrayList<>();

  protected State() {}

  public boolean isEnd() {
    return isEnd;
  }

  public boolean isNumbered() {
    return number != UNASSIGNED;
  }

  public int number() {
    return number;
  }

  public D diagram() {
    return diagram;
  }

  public List<TypeParameter> parameters() {
    return new ArrayList<>(parameters);
  }

  public List<TypeReference> typeReferences() {
    return new ArrayList<>(typeReferences);
  }

  public List<T> transitions() {
    return new ArrayList<>(transitions);
  }
}
