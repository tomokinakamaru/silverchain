package silverchain.parser;

import static silverchain.diagram.Builders.merge;

import silverchain.diagram.Diagram;

public final class RuleExpression extends ASTNodeN<RuleTerm, RuleExpression> {

  public RuleExpression(Range range, RuleTerm head, RuleExpression tail) {
    super(range, head, tail);
  }

  @Override
  Diagram reduce(Diagram diagram1, Diagram diagram2) {
    return merge(diagram1, diagram2);
  }

  @Override
  String separator() {
    return "|";
  }
}
