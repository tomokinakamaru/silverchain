package silverchain.grammar;

import java.util.Optional;
import java.util.Set;

public final class TypeParameter extends ASTNode2<String, TypeParameterBound> {

  public TypeParameter(String name, TypeParameterBound bound) {
    super(name, bound);
  }

  public String name() {
    return left();
  }

  public Optional<TypeParameterBound> bound() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return name() + bound().map(TypeParameterBound::toString).orElse("");
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    bound().ifPresent(b -> b.resolveReferences(parameters));
  }
}
