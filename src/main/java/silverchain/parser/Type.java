package silverchain.parser;

import static silverchain.diagram.Builders.atom;

import java.util.Map;
import java.util.Optional;
import silverchain.diagram.Diagram;

public final class Type extends ASTNode2<QualifiedName, TypeParameters> {

  public Type(Range range, QualifiedName name, TypeParameters parameters) {
    super(range, name, parameters);
  }

  public QualifiedName name() {
    return left();
  }

  public Optional<TypeParameters> parameters() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return name() + parameters().map(TypeParameters::toString).orElse("");
  }

  @Override
  public Diagram diagram(Map<String, QualifiedName> importMap) {
    if (!name().qualifier().isPresent()) {
      if (importMap.containsKey(name().name())) {
        left = importMap.get(name().name());
      }
    }
    return atom(this);
  }
}
