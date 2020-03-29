package silverchain.grammar;

import static silverchain.graph.GraphBuilders.atom;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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

  public List<TypeParameter> referents() {
    Set<TypeParameter> parameters = new LinkedHashSet<>();
    if (referent != null) {
      parameters.add(referent);
    }
    arguments().ifPresent(a -> parameters.addAll(a.referents()));
    return new ArrayList<>(parameters);
  }

  @Override
  public String toString() {
    return name().toString() + arguments().map(a -> "[" + a.toString() + "]").orElse("");
  }

  public Graph graph() {
    return atom(this);
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (!name().qualifier().isPresent()) {
      String name = name().name();
      referent = parameters.stream().filter(p -> p.name().equals(name)).findFirst().orElse(null);
    }
    arguments().ifPresent(a -> a.resolveReferences(parameters));
  }
}
