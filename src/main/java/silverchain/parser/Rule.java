package silverchain.parser;

import static silverchain.graph.GraphBuilders.join;

import java.util.Optional;
import silverchain.graph.Graph;

public final class Rule extends ASTNode2<RuleExpression, TypeReference> {

  public Rule(Range range, RuleExpression expression, TypeReference type) {
    super(range, expression, type);
  }

  public RuleExpression expression() {
    return left();
  }

  public Optional<TypeReference> type() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return expression() + type().map(t -> " " + t).orElse("") + ";";
  }

  @Override
  Graph reduce(Graph graph1, Graph graph2) {
    return join(graph1, graph2);
  }
}
