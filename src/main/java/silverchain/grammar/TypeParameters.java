package silverchain.grammar;

import java.util.Optional;

public final class TypeParameters extends ASTNode2<TypeParameterList, TypeParameterList> {

  public TypeParameters(Range range, TypeParameterList publicList, TypeParameterList privateList) {
    super(range, publicList, privateList);
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
}
