package silverchain.parser;

import static silverchain.diagram.Builders.atom;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import silverchain.diagram.Diagram;

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
  public List<TypeParameter> referents() {
    return referent() == null ? super.referents() : make(referent(), super.referents());
  }

  @Override
  void resolveReferences(List<TypeParameter> typeParameters) {
    if (!name().qualifier().isPresent()) {
      referent = find(name(), typeParameters);
    }
    super.resolveReferences(typeParameters);
  }

  @Override
  public Diagram diagram() {
    return atom(this);
  }

  private static List<TypeParameter> make(TypeParameter parameter, List<TypeParameter> list) {
    Set<TypeParameter> set = new LinkedHashSet<>();
    set.add(parameter);
    set.addAll(list);
    return new ArrayList<>(set);
  }

  private static TypeParameter find(QualifiedName needle, List<TypeParameter> haystack) {
    return haystack.stream().filter(p -> p.name().equals(needle.name())).findFirst().orElse(null);
  }
}
