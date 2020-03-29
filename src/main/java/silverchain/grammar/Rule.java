package silverchain.grammar;

import static silverchain.graph.GraphBuilders.join;

import java.util.Optional;
import java.util.Set;
import silverchain.graph.Graph;

public final class Rule extends ASTNode2<RuleExpression, TypeReference> {

  public Rule(RuleExpression expression, TypeReference type) {
    super(expression, type);
  }

  public RuleExpression expression() {
    return left();
  }

  public Optional<TypeReference> type() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return expression().toString() + type().map(t -> " " + t.toString()).orElse("") + ";";
  }

  public Graph graph() {
    Graph g = expression().graph();
    return type().map(t -> join(g, t.graph())).orElse(g);
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    expression().resolveReferences(parameters);
    type().ifPresent(t -> t.resolveReferences(parameters));
  }
}
