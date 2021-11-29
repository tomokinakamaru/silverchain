package silverchain.parser;

import static java.util.stream.Stream.generate;
import static silverchain.diagram.Builders.*;

import java.util.Map;
import java.util.Optional;
import silverchain.diagram.Builders;
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
    Diagram diagram = element().diagram(importMap);
    if (!operator().isPresent()) {
      return diagram;
    }

    int min = operator().get().min();
    Integer max = operator().get().max().orElse(null);
    Diagram first = generate(() -> diagram).limit(min).reduce(Builders::join).orElse(atom());
    Diagram second;
    if (max == null) {
      second = repeat(diagram);
    } else {
      Diagram d = merge(diagram, atom());
      second = generate(() -> d).limit(max - min).reduce(Builders::join).orElse(atom());
    }
    return join(first, second);
  }
}
