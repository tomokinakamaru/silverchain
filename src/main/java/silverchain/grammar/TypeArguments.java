package silverchain.grammar;

public final class TypeArguments extends ASTNodeN<TypeArgument, TypeArguments> {

  public TypeArguments(TypeArgument head, TypeArguments tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    String s = tail() == null ? "" : "," + tail().toString();
    return head().toString() + s;
  }
}
