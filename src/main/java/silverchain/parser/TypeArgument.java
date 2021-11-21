package silverchain.parser;

import java.util.Optional;

public final class TypeArgument extends ASTNode2<TypeReference, TypeParameterBound> {

  public TypeArgument(Range range, TypeReference left, TypeParameterBound right) {
    super(range, left, right);
  }

  public Optional<TypeReference> reference() {
    return Optional.ofNullable(left());
  }

  public Optional<TypeParameterBound> bound() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    if (reference().isPresent()) {
      return reference().get().toString();
    }
    if (bound().isPresent()) {
      return "? " + bound().get();
    }
    return "?";
  }
}
