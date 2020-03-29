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
    tail().ifPresent(t -> list.addAll(t.typeParameters()));
    return list;
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    head().resolveReferences(parameters);
    tail().ifPresent(t -> t.resolveReferences(parameters));
  }

  @Override
  String separator() {
    return " ";
  }
}
