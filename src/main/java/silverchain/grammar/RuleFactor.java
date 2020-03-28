package silverchain.grammar;

public final class RuleFactor extends ASTNode2<RuleElement, RepeatOperator> {

  public RuleFactor(RuleElement element, RepeatOperator operator) {
    super(element, operator);
  }

  public RuleElement element() {
    return left();
  }

  public RepeatOperator operator() {
    return right();
  }

  @Override
  public String toString() {
    String s = operator() == null ? "" : operator().toString();
    return element().toString() + s;
  }

  @Override
  public void accept(Visitor visitor) {
    super.accept(visitor);
    visitor.visit(this);
  }
}
