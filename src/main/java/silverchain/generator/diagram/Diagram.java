package silverchain.generator.diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import silverchain.grammar.QualifiedName;
import silverchain.grammar.TypeParameter;

public abstract class Diagram<
    D extends Diagram<D, S, T>, S extends State<D, S, T>, T extends Transition<D, S, T>> {

  QualifiedName name;

  final List<TypeParameter> parameters = new ArrayList<>();

  final List<S> states = new ArrayList<>();

  public List<S> states() {
    return new ArrayList<>(states);
  }

  public List<S> numberedStates() {
    return states.stream().filter(S::isNumbered).collect(Collectors.toCollection(ArrayList::new));
  }

  public QualifiedName name() {
    return name;
  }

  public List<TypeParameter> parameters() {
    return new ArrayList<>(parameters);
  }
}
