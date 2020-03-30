package silverchain.grammar;

import java.util.List;
import java.util.Set;

public final class MethodParameter extends ASTNode2<TypeReference, String> {

  public MethodParameter(TypeReference typeReference, String name) {
    super(typeReference, name);
  }

  public TypeReference type() {
    return left();
  }

  public String name() {
    return right();
  }

  public List<TypeParameter> referents() {
    return type().referents();
  }

  @Override
  public String toString() {
    return type().toString() + " " + name();
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    type().resolveReferences(parameters);
  }
}
