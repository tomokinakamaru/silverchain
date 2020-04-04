package silverchain.parser;

import static silverchain.graph.GraphBuilders.atom;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import silverchain.graph.Graph;

public final class TypeReference extends ASTNode2<QualifiedName, TypeReferences> {

  private TypeParameter referent;

  TypeReference(Range range, QualifiedName name, TypeReferences arguments) {
    super(range, name, arguments);
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

  @Override
  public String toString() {
    return name() + arguments().map(a -> "[" + a + "]").orElse("");
  }

  @Override
  public Set<TypeParameter> referents() {
    return referent == null ? super.referents() : union(referent, super.referents());
  }

  @Override
  void resolveReferences(Set<TypeParameter> typeParameters) {
    if (!name().qualifier().isPresent()) {
      referent = find(name(), typeParameters);
    }
    super.resolveReferences(typeParameters);
  }

  @Override
  public Graph graph() {
    return atom(this);
  }

  private static Set<TypeParameter> union(TypeParameter parameter, Set<TypeParameter> parameters) {
    Set<TypeParameter> set = new LinkedHashSet<>();
    set.add(parameter);
    set.addAll(parameters);
    return set;
  }

  private static TypeParameter find(QualifiedName needle, Set<TypeParameter> haystack) {
    return haystack.stream().filter(p -> p.name().equals(needle.name())).findFirst().orElse(null);
  }
}
