package silverchain.analyzer;

import java.util.ArrayList;
import java.util.List;
import silverchain.grammar.Grammar;
import silverchain.grammar.Grammars;
import silverchain.graph.GraphNode;

public final class Analyzer {

  private final Grammars grammars;

  public Analyzer(Grammars grammars) {
    this.grammars = grammars;
  }

  public List<List<GraphNode>> analyze() {
    List<List<GraphNode>> list = new ArrayList<>();
    for (Grammar grammar : grammars.list()) {
      new ReferenceResolver().apply(grammar);
      list.add(new GraphBuilder().apply(grammar).compile(new GraphOption()));
    }
    return list;
  }
}
