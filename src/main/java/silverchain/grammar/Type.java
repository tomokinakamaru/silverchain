package silverchain.grammar;

import static silverchain.graph.GraphBuilders.atom;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    return name().toString() + parameters().map(p -> "[" + p.toString() + "]").orElse("");
  }

  public Graph graph() {
    return atom(this);
  }

  public List<TypeParameter> typeParameters() {
    return parameters().map(TypeParameters::list).orElse(Collections.emptyList());
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    parameters().ifPresent(p -> p.resolveReferences(parameters));
  }
}
