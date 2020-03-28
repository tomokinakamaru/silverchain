package silverchain.grammar;

public final class Rule extends ASTNode2<RuleExpression, TypeReference> {

  public Rule(RuleExpression expression, TypeReference type) {
    super(expression, type);
  }

  public RuleExpression expression() {
    return left();
  }

  public TypeReference type() {
    return right();
  }

  @Override
  public String toString() {
    String s = type() == null ? "" : " " + type().toString();
    return expression().toString() + s + ";";
  }
}
