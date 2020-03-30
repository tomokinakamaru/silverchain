package silverchain.grammar;

import java.util.Set;

public final class TypeParameterBound extends ASTNode2<Boolean, TypeReference> {

  public TypeParameterBound(boolean isUpper, TypeReference reference) {
    super(isUpper, reference);
  }

  public boolean isUpper() {
    return left();
  }

  public TypeReference reference() {
    return right();
  }

  @Override
  public String toString() {
    return (isUpper() ? "<:" : ":>") + " " + reference().toString();
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    reference().resolveReferences(parameters);
  }
}
