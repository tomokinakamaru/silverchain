package silverchain.grammar;

import java.util.Optional;

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
}
