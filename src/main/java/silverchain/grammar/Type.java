package silverchain.grammar;

import static silverchain.graph.GraphBuilders.atom;

import java.util.Optional;
import silverchain.graph.Graph;

public final class Type extends ASTNode2<QualifiedName, TypeParameters> {

  public Type(QualifiedName name, TypeParameters parameters) {
    super(name, parameters);
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

  public Graph graph() {
    return atom(this);
  }
}
