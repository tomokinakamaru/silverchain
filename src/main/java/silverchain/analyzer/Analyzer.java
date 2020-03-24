package silverchain.analyzer;

import java.util.List;
import silverchain.grammar.Grammar;
import silverchain.graph.Graph;
import silverchain.graph.GraphNode;

public final class Analyzer {

  private final Grammar grammar;

  public Analyzer(Grammar grammar) {
    this.grammar = grammar;
  }

  public List<GraphNode> analyze() {
    return graph().compile(option());
  }

  private Graph graph() {
    return new GraphBuilder().apply(grammar);
  }

  private GraphOption option() {
    new ReferenceResolver().apply(grammar);
    return new GraphOption();
  }
}
