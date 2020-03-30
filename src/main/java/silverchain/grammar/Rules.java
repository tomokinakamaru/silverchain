package silverchain.grammar;

import static silverchain.graph.GraphBuilders.merge;

import silverchain.graph.Graph;

public final class Rules extends ASTNodeN<Rule, Rules> {

  public Rules(Rule head, Rules tail) {
    super(head, tail);
  }

  @Override
  Graph reduce(Graph graph1, Graph graph2) {
    return merge(graph1, graph2);
  }

  @Override
  String separator() {
    return " ";
  }
}
