package silverchain.analyzer;

import silverchain.grammar.Grammar;
import silverchain.graph.Graph;
import silverchain.graph.GraphCompileOption;

public final class Analyzer {

  private final Grammar grammar;

  public Analyzer(Grammar grammar) {
    this.grammar = grammar;
  }

  public Graph graph() {
    return new GraphBuilder().apply(grammar);
  }

  public GraphCompileOption option() {
    return new GraphOption(new ParamDefCollector().apply(grammar));
  }
}
