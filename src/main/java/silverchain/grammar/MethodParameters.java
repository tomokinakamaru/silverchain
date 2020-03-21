package silverchain.grammar;

public final class MethodParameters extends ASTNodeN<MethodParameter, MethodParameters> {

  public MethodParameters(MethodParameter head, MethodParameters tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    String s = tail() == null ? "" : "," + tail().toString();
    return head().toString() + s;
  }
}
