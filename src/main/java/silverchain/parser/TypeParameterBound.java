package silverchain.parser;

public final class TypeParameterBound extends ASTNode2<Boolean, TypeReference> {

  public TypeParameterBound(Range range, boolean isUpper, TypeReference reference) {
    super(range, isUpper, reference);
  }

  public boolean isUpper() {
    return left();
  }

  public TypeReference reference() {
    return right();
  }

  @Override
  public String toString() {
    return (isUpper() ? "extends " : "super ") + reference();
  }
}
