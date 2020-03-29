package silverchain.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class TypeArguments extends ASTNodeN<TypeArgument, TypeArguments> {

  public TypeArguments(TypeArgument head, TypeArguments tail) {
    super(head, tail);
  }

  public List<TypeParameter> referents() {
    return StreamSupport.stream(spliterator(), false)
        .map(TypeArgument::referents)
        .flatMap(Collection::stream)
        .distinct()
        .collect(Collectors.toCollection(ArrayList::new));
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
    return ",";
  }
}
