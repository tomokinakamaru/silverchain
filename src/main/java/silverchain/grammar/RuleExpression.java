package silverchain.grammar;

public final class RuleExpression extends ASTNodeN<RuleTerm, RuleExpression> {

  public RuleExpression(RuleTerm head, RuleExpression tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    String s = tail() == null ? "" : "|" + tail().toString();
    return head().toString() + s;
  }
}
