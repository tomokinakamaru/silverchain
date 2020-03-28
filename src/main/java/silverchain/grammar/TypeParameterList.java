package silverchain.grammar;

import java.util.stream.Collectors;

public final class TypeParameterList extends ASTNodeN<TypeParameter, TypeParameterList> {

  public TypeParameterList(TypeParameter head, TypeParameterList tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    return stream().map(TypeParameter::toString).collect(Collectors.joining(","));
  }
}
