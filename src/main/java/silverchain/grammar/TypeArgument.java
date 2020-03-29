package silverchain.grammar;

import java.util.List;
import java.util.Set;

public final class TypeArgument extends ASTNode1<TypeReference> {

  public TypeArgument(TypeReference reference) {
    super(reference);
  }

  public TypeReference reference() {
    return object();
  }

  public List<TypeParameter> referents() {
    return reference().referents();
  }

  @Override
  public String toString() {
    return reference().toString();
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (reference() != null) {
      reference().resolveReferences(parameters);
    }
  }
}
