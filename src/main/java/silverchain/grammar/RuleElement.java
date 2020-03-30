package silverchain.grammar;

import java.util.Optional;
import java.util.Set;
import silverchain.graph.Graph;

public final class RuleElement extends ASTNode2<Method, RuleExpression> {

  public RuleElement(Method method, RuleExpression expression) {
    super(method, expression);
  }

  public Optional<Method> method() {
    return Optional.ofNullable(left());
  }

  public Optional<RuleExpression> expression() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return method().map(Method::toString).orElse("")
        + expression().map(e -> "(" + e + ")").orElse("");
  }

  public Graph graph() {
    return method().map(Method::graph).orElse(expression().map(RuleExpression::graph).orElse(null));
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    method().ifPresent(m -> m.resolveReferences(parameters));
    expression().ifPresent(e -> e.resolveReferences(parameters));
  }
}
