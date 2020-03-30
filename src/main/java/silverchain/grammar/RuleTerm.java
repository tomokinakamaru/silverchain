package silverchain.grammar;

import static silverchain.graph.GraphBuilders.join;

import silverchain.graph.Graph;

public final class RuleTerm extends ASTNodeN<RuleFactor, RuleTerm> {

  public RuleTerm(RuleFactor head, RuleTerm tail) {
    super(head, tail);
  }

  @Override
  Graph reduce(Graph graph1, Graph graph2) {
    return join(graph1, graph2);
  }

  @Override
  String separator() {
    return " ";
  }
}
