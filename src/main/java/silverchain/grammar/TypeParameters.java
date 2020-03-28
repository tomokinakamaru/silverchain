package silverchain.grammar;

public final class TypeParameters extends ASTNode2<TypeParameterList, TypeParameterList> {

  public TypeParameters(TypeParameterList publicList, TypeParameterList privateList) {
    super(publicList, privateList);
  }

  public TypeParameterList publicList() {
    return left();
  }

  public TypeParameterList privateList() {
    return right();
  }

  @Override
  public String toString() {
    String s = publicList() == null ? "" : publicList().toString();
    String t = privateList() == null ? "" : ";" + privateList().toString();
    return s + t;
  }

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visit(this);
  }
}
