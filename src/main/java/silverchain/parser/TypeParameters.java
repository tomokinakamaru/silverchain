package silverchain.parser;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class TypeParameters extends ASTNode2<TypeParameterList, TypeParameterList> {

  public TypeParameters(Range range, TypeParameterList publicList, TypeParameterList privateList) {
    super(range, publicList, privateList);
  }

  public Optional<TypeParameterList> publicList() {
    return Optional.ofNullable(left());
  }

  public Optional<TypeParameterList> privateList() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return "<"
        + publicList().map(ASTNodeN::toString).orElse("")
        + privateList().map(p -> ";" + p).orElse("")
        + ">";
  }

  @Override
  public void validate() {
    Set<String> defined = new HashSet<>();
    for (TypeParameter p : typeParameters()) {
      if (defined.contains(p.name())) {
        throw new DuplicateDeclaration(p);
      }
      defined.add(p.name());
    }
    super.validate();
  }
}
