package silverchain.grammar;

import java.util.stream.Collectors;

public final class RuleExpression extends ASTNodeN<RuleTerm, RuleExpression> {

  public RuleExpression(RuleTerm head, RuleExpression tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    return toList().stream().map(RuleTerm::toString).collect(Collectors.joining("|"));
  }
}
