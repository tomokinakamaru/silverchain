package silverchain.parser;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.iterators.PermutationIterator;
import silverchain.diagram.Builders;
import silverchain.diagram.Diagram;

public final class RuleElement extends ASTNode2<Method, RuleExpressions> {

  private final boolean isSet;

  public RuleElement(Range range, Method method, RuleExpressions expressions, boolean isSet) {
    super(range, method, expressions);
    this.isSet = isSet;
  }

  public Optional<Method> method() {
    return Optional.ofNullable(left());
  }

  public Optional<RuleExpressions> expressions() {
    return Optional.ofNullable(right());
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Override
  public String toString() {
    if (method().isPresent()) {
      return method().get().toString();
    }

    RuleExpressions expressions = expressions().get();
    if (isSet) {
      return "{" + expressions.stream().map(ASTNodeN::toString).collect(joining(",")) + "}";
    } else {
      return "(" + expressions.stream().findFirst().orElse(null) + ")";
    }
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Override
  public Diagram diagram(Map<String, QualifiedName> importMap) {
    if (method().isPresent()) {
      return method().get().diagram(importMap);
    }

    List<RuleExpression> expressions = expressions().get().stream().collect(Collectors.toList());
    if (expressions.size() == 1) {
      return expressions.get(0).diagram(importMap);
    }

    PermutationIterator<RuleExpression> i = new PermutationIterator<>(expressions);
    List<Diagram> diagrams = new ArrayList<>();
    while (i.hasNext()) {
      List<RuleExpression> es = i.next();
      Diagram d = es.stream().map(n -> n.diagram(importMap)).reduce(Builders::join).orElse(null);
      diagrams.add(d);
    }
    return diagrams.stream().reduce(Builders::merge).orElse(null);
  }
}
