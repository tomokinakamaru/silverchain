package silverchain.parser;

import java.util.Optional;

public final class RuleElement extends ASTNode2<Method, RuleExpression> {

  RuleElement(Range range, Method method, RuleExpression expression) {
    super(range, method, expression);
  }

  public Optional<Method> method() {
    return Optional.ofNullable(left());
  }

  public Optional<RuleExpression> expression() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return method().map(Method::toString).orElse(expression().map(e -> "(" + e + ")").orElse(""));
  }
}
