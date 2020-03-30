package silverchain.grammar;

import java.util.Set;

public final class TypeParameterList extends ASTNodeN<TypeParameter, TypeParameterList> {

  public TypeParameterList(TypeParameter head, TypeParameterList tail) {
    super(head, tail);
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    forEach(t -> t.resolveReferences(parameters));
  }

  @Override
  String separator() {
    return ",";
  }
}
