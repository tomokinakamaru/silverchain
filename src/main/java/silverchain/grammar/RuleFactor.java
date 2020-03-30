package silverchain.grammar;

import static silverchain.graph.GraphBuilders.repeat;

import java.util.Optional;
import java.util.Set;
import silverchain.graph.Graph;

public final class RuleFactor extends ASTNode2<RuleElement, RepeatOperator> {

  public RuleFactor(RuleElement element, RepeatOperator operator) {
    super(element, operator);
  }

  public RuleElement element() {
    return left();
  }

  public Optional<RepeatOperator> operator() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return element().toString() + operator().map(RepeatOperator::toString).orElse("");
  }

  public Graph graph() {
    Graph g = element().graph();
    return operator().map(o -> repeat(g, o.min(), o.max().orElse(null))).orElse(g);
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    element().resolveReferences(parameters);
  }
}
