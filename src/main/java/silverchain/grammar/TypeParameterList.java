package silverchain.grammar;

public final class TypeParameterList extends ASTNodeN<TypeParameter, TypeParameterList> {

  public TypeParameterList(TypeParameter head, TypeParameterList tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    String s = tail() == null ? "" : "," + tail().toString();
    return head().toString() + s;
  }
}
