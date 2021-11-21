package silverchain.parser;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class TypeParameter extends ASTNode2<String, TypeParameterBound> {

  public TypeParameter(Range range, String name, TypeParameterBound bound) {
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
  public List<TypeParameter> typeParameters() {
    return Collections.singletonList(this);
  }
}
