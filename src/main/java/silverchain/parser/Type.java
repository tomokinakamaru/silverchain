package silverchain.parser;

import static silverchain.graph.GraphBuilders.atom;

import java.util.Optional;
import silverchain.graph.Graph;

public final class Type extends ASTNode2<QualifiedName, TypeParameters> {

  Type(Range range, QualifiedName name, TypeParameters parameters) {
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
    return name() + parameters().map(p -> "[" + p + "]").orElse("");
  }

  @Override
  public Graph graph() {
    return atom(this);
  }
}
