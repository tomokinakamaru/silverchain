package silverchain.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Grammars extends ASTNodeN<Grammar, Grammars> {

  public Grammars(Grammar head, Grammars tail) {
    super(head, tail);
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

  @Override
  String separator() {
    return " ";
  }
}
