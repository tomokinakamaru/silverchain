package silverchain.parser;

public final class FormalParameter extends ASTNode2<TypeReference, String> {

  FormalParameter(Range range, TypeReference typeReference, String name) {
    super(range, typeReference, name);
  }

  public TypeReference type() {
    return left();
  }

  public String name() {
    return right();
  }

  @Override
  public String toString() {
    return type() + " " + name();
  }
}
