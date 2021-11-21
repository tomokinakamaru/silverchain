package silverchain.parser;

public final class FormalParameters extends ASTNodeN<FormalParameter, FormalParameters> {

  public FormalParameters(Range range, FormalParameter head, FormalParameters tail) {
    super(range, head, tail);
  }
}
