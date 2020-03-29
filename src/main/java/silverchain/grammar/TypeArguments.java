package silverchain.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class TypeArguments extends ASTNodeN<TypeArgument, TypeArguments> {

  public TypeArguments(TypeArgument head, TypeArguments tail) {
    super(head, tail);
  }

  public List<TypeParameter> referents() {
    return stream()
        .map(TypeArgument::referents)
        .flatMap(Collection::stream)
        .distinct()
        .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public String toString() {
    return stream().map(TypeArgument::toString).collect(Collectors.joining(","));
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
