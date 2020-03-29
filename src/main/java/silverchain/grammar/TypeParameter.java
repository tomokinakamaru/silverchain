package silverchain.grammar;

import java.util.Set;

public final class TypeParameter extends ASTNode2<String, TypeParameterBound> {

  public TypeParameter(String name, TypeParameterBound bound) {
    super(name, bound);
  }

  public String name() {
    return left();
  }

  public TypeParameterBound bound() {
    return right();
  }

  @Override
  public String toString() {
    return name() + (bound() == null ? "" : bound().toString());
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (bound() != null) {
      bound().resolveReferences(parameters);
    }
  }
}
