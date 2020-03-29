package silverchain.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class Grammars extends ASTNodeN<Grammar, Grammars> {

  public Grammars(Grammar head, Grammars tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    return stream().map(Grammar::toString).collect(Collectors.joining(" "));
  }

  public List<TypeParameter> typeParameters() {
    List<TypeParameter> list = new ArrayList<>(head().typeParameters());
    if (tail() != null) {
      list.addAll(tail().typeParameters());
    }
    return list;
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (head() != null) {
      head().resolveReferences(parameters);
    }
    if (tail() != null) {
      tail().resolveReferences(parameters);
    }
  }
}
