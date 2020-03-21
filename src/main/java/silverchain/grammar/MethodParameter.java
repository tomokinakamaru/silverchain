package silverchain.grammar;

public final class MethodParameter extends ASTNode2<TypeReference, String> {

  public MethodParameter(TypeReference typeReference, String name) {
    super(typeReference, name);
  }

  public TypeReference type() {
    return left();
  }

  public String name() {
    return right();
  }

  @Override
  public String toString() {
    return type().toString() + " " + name();
  }
}
