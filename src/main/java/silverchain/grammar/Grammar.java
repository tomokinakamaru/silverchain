package silverchain.grammar;

import static silverchain.graph.GraphBuilders.join;

import java.util.Optional;
import silverchain.graph.Graph;

public final class Grammar extends ASTNode2<Type, Rules> {

  public Grammar(Type type, Rules rules) {
    super(type, rules);
  }

  public Type type() {
    return left();
  }

  public Optional<Rules> rules() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return type() + ":" + rules().map(r -> " " + r).orElse("");
  }

  @Override
  Graph reduce(Graph graph1, Graph graph2) {
    return join(graph1, graph2);
  }
}
