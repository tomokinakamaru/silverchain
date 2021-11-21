package silverchain.parser;

import java.util.Optional;

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
    return type().map(TypeReference::toString).orElse("void") + " " + expression() + ";";
  }
}
