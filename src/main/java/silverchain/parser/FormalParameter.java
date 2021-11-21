package silverchain.parser;

public final class FormalParameter extends ASTNode2<TypeReference, String> {

  private boolean isVarArgs;

  public FormalParameter(Range range, TypeReference typeReference, String name, boolean isVarArgs) {
    super(range, typeReference, name);
    this.isVarArgs = isVarArgs;
  }

  public TypeReference type() {
    return left();
  }

  public String name() {
    return right();
  }

  public boolean isVarArgs() {
    return isVarArgs;
  }

  @Override
  public String toString() {
    return type() + (isVarArgs ? "..." : "") + " " + name();
  }
}
