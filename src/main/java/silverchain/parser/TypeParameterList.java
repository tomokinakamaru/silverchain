package silverchain.parser;

public final class TypeParameterList extends ASTNodeN<TypeParameter, TypeParameterList> {

  public TypeParameterList(Range range, TypeParameter head, TypeParameterList tail) {
    super(range, head, tail);
  }
}
