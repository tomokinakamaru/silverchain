package silverchain.grammar;

public final class Type extends ASTNode2<QualifiedName, TypeParameters> {

  public Type(QualifiedName name, TypeParameters parameters) {
    super(name, parameters);
  }

  public QualifiedName name() {
    return left();
  }

  public TypeParameters parameters() {
    return right();
  }

  @Override
  public String toString() {
    String s = parameters() == null ? "" : "<" + parameters().toString() + ">";
    return name().toString() + s;
  }

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visit(this);
  }
}
