package silverchain.grammar;

import static silverchain.graph.GraphBuilders.repeat;

import java.util.Optional;
import silverchain.graph.Graph;

public final class RuleFactor extends ASTNode2<RuleElement, RepeatOperator> {

  public RuleFactor(Range range, RuleElement element, RepeatOperator operator) {
    super(range, element, operator);
  }

  public RuleElement element() {
    return left();
  }

  public Optional<RepeatOperator> operator() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return element() + operator().map(RepeatOperator::toString).orElse("");
  }

  @Override
  public Graph graph() {
    Graph g = element().graph();
    return operator().map(o -> repeat(g, o.min(), o.max().orElse(null))).orElse(g);
  }
}
