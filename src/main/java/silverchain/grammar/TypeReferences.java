package silverchain.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class TypeReferences extends ASTNodeN<TypeReference, TypeReferences> {

  public TypeReferences(TypeReference head, TypeReferences tail) {
    super(head, tail);
  }

  public List<TypeParameter> referents() {
    return StreamSupport.stream(spliterator(), false)
        .map(TypeReference::referents)
        .flatMap(Collection::stream)
        .distinct()
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    forEach(t -> t.resolveReferences(parameters));
  }

  @Override
  String separator() {
    return ",";
  }
}
