package silverchain.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class TypeParameters extends ASTNode2<TypeParameterList, TypeParameterList> {

  public TypeParameters(TypeParameterList publicList, TypeParameterList privateList) {
    super(publicList, privateList);
  }

  public Optional<TypeParameterList> publicList() {
    return Optional.ofNullable(left());
  }

  public Optional<TypeParameterList> privateList() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return publicList().map(ASTNodeN::toString).orElse("")
        + privateList().map(p -> ";" + p).orElse("");
  }

  List<TypeParameter> list() {
    List<TypeParameter> parameters = new ArrayList<>();
    publicList().ifPresent(p -> p.forEach(parameters::add));
    privateList().ifPresent(p -> p.forEach(parameters::add));
    return parameters;
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    publicList().ifPresent(p -> p.resolveReferences(parameters));
    privateList().ifPresent(p -> p.resolveReferences(parameters));
  }
}
