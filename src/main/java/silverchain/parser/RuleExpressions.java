package silverchain.parser;

public final class RuleExpressions extends ASTNodeN<RuleExpression, RuleExpressions> {

  public RuleExpressions(Range range, RuleExpression head, RuleExpressions tail) {
    super(range, head, tail);
  }
}
