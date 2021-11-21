package silverchain.parser;

import static silverchain.diagram.Builders.atom;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import silverchain.diagram.Diagram;

public final class TypeReference extends ASTNode2<QualifiedName, TypeArguments> {

  private TypeParameter referent;

  private final boolean isArray;

  public TypeReference(Range range, QualifiedName name, TypeArguments arguments, boolean isArray) {
    super(range, name, arguments);
    this.isArray = isArray;
  }

  public QualifiedName name() {
    return left();
  }

  public Optional<TypeArguments> arguments() {
    return Optional.ofNullable(right());
  }

  public TypeParameter referent() {
    return referent;
  }

  public boolean isArray() {
    return isArray;
  }

  @Override
  public String toString() {
    return name() + arguments().map(a -> "<" + a + ">").orElse("") + (isArray ? "[]" : "");
  }

  @Override
  public List<TypeParameter> referents() {
    return referent() == null ? super.referents() : make(referent(), super.referents());
  }

  @Override
  void resolveReferences(List<TypeParameter> typeParameters, Map<String, QualifiedName> importMap) {
    if (!name().qualifier().isPresent()) {
      referent = find(name(), typeParameters);
      if (importMap.containsKey(name().name())) {
        left = importMap.get(name().name());
      }
    }
    super.resolveReferences(typeParameters, importMap);
  }

  @Override
  public Diagram diagram(Map<String, QualifiedName> importMap) {
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
