package silverchain.grammar;

public final class Grammar extends ASTNode2<Type, Rules> {

  public Grammar(Type type, Rules rules) {
    super(type, rules);
  }

  public Type type() {
    return left();
  }

  public Rules rules() {
    return right();
  }

  @Override
  public String toString() {
    String s = rules() == null ? "" : " " + rules().toString();
    return type().toString() + ":" + s;
  }

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visit(this);
  }
}
