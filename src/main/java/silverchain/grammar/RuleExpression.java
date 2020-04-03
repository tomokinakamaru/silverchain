package silverchain.grammar;

import static silverchain.graph.GraphBuilders.merge;

import silverchain.graph.Graph;

public final class RuleExpression extends ASTNodeN<RuleTerm, RuleExpression> {

  public RuleExpression(Range range, RuleTerm head, RuleExpression tail) {
    super(range, head, tail);
  }

  @Override
  Graph reduce(Graph graph1, Graph graph2) {
    return merge(graph1, graph2);
  }

  @Override
  String separator() {
    return "|";
  }
}
