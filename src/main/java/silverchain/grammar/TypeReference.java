package silverchain.grammar;

import static silverchain.graph.GraphBuilders.atom;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import silverchain.graph.Graph;

public final class TypeReference extends ASTNode2<QualifiedName, TypeReferences> {

  private TypeParameter referent;

  public TypeReference(QualifiedName name, TypeReferences arguments) {
    super(name, arguments);
  }

  public QualifiedName name() {
    return left();
  }

  public Optional<TypeReferences> arguments() {
    return Optional.ofNullable(right());
  }

  public TypeParameter referent() {
    return referent;
  }

  public Set<TypeParameter> referents() {
    Set<TypeParameter> parameters = new LinkedHashSet<>();
    if (referent != null) {
      parameters.add(referent);
    }
    parameters.addAll(super.referents());
    return parameters;
  }

  @Override
  public String toString() {
    return name() + arguments().map(a -> "[" + a + "]").orElse("");
  }

  public Graph graph() {
    return atom(this);
  }

  @Override
  public void resolveReferences(Set<TypeParameter> parameters) {
    if (!name().qualifier().isPresent()) {
      String name = name().name();
      referent = parameters.stream().filter(p -> p.name().equals(name)).findFirst().orElse(null);
    }
    arguments().ifPresent(a -> a.resolveReferences(parameters));
  }
}
