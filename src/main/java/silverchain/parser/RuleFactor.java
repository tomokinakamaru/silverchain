package silverchain.parser;

import static silverchain.diagram.Builders.repeat;

import java.util.Map;
import java.util.Optional;
import silverchain.diagram.Diagram;

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
  public Diagram diagram(Map<String, QualifiedName> importMap) {
    Diagram d = element().diagram(importMap);
    return operator().map(o -> repeat(d, o.min(), o.max().orElse(null))).orElse(d);
  }
}
