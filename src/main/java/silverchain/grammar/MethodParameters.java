package silverchain.grammar;

public final class MethodParameters extends ASTNodeN<MethodParameter, MethodParameters> {

  public MethodParameters(MethodParameter head, MethodParameters tail) {
    super(head, tail);
  }

  @Override
  String separator() {
    return ",";
  }
}
