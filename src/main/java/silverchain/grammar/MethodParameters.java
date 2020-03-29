package silverchain.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class MethodParameters extends ASTNodeN<MethodParameter, MethodParameters> {

  public MethodParameters(MethodParameter head, MethodParameters tail) {
    super(head, tail);
  }

  public List<TypeParameter> referents() {
    return stream()
        .map(MethodParameter::referents)
        .flatMap(Collection::stream)
        .distinct()
        .collect(Collectors.toCollection(ArrayList::new));
  }

  @Override
  public String toString() {
    return stream().map(MethodParameter::toString).collect(Collectors.joining(","));
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
