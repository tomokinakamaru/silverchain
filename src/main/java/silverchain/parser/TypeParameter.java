package silverchain.parser;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public final class TypeParameter extends ASTNode2<String, TypeParameterBound> {

  TypeParameter(Range range, String name, TypeParameterBound bound) {
    super(range, name, bound);
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

  @Override
  public Set<TypeParameter> typeParameters() {
    return Collections.singleton(this);
  }
}
