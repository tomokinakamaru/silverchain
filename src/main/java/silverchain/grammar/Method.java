package silverchain.grammar;

public final class Method extends ASTNode2<String, MethodParameters> {

  public Method(String name, MethodParameters parameters) {
    super(name, parameters);
  }

  public String name() {
    return left();
  }

  public MethodParameters parameters() {
    return right();
  }

  @Override
  public String toString() {
    String s = parameters() == null ? "" : parameters().toString();
    return name() + "(" + s + ")";
  }
}
