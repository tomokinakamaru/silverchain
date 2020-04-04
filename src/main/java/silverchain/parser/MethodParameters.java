package silverchain.parser;

public final class MethodParameters extends ASTNodeN<MethodParameter, MethodParameters> {

  MethodParameters(Range range, MethodParameter head, MethodParameters tail) {
    super(range, head, tail);
  }
}
