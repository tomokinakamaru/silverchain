package silverchain.parser;

import java.util.Map;
import java.util.Optional;
import silverchain.diagram.Diagram;

public final class Grammar extends ASTNode2<Type, Rules> {

  public Grammar(Range range, Type type, Rules rules) {
    super(range, type, rules);
  }

  public Type type() {
    return left();
  }

  public Optional<Rules> rules() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return type() + " {" + rules().map(r -> " " + r + " }").orElse("}");
  }

  @Override
  public Diagram diagram(Map<String, QualifiedName> importMap) {
    resolveReferences(typeParameters(), importMap);
    return super.diagram(importMap);
  }
}
