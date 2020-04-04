package silverchain.parser;

public final class RuleTerm extends ASTNodeN<RuleFactor, RuleTerm> {

  public RuleTerm(Range range, RuleFactor head, RuleTerm tail) {
    super(range, head, tail);
  }

  @Override
  String separator() {
    return " ";
  }
}
