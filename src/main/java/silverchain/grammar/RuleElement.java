package silverchain.grammar;

public final class RuleElement extends ASTNode2<Method, RuleExpression> {

  public RuleElement(Method method, RuleExpression expression) {
    super(method, expression);
  }

  public Method method() {
    return left();
  }

  public RuleExpression expression() {
    return right();
  }

  @Override
  public String toString() {
    if (method() == null) {
      return "(" + expression().toString() + ")";
    }
    return method().toString();
  }
}
