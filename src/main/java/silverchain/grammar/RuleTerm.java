package silverchain.grammar;

public final class RuleTerm extends ASTNodeN<RuleFactor, RuleTerm> {

  public RuleTerm(RuleFactor head, RuleTerm tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    String s = tail() == null ? "" : " " + tail().toString();
    return head().toString() + s;
  }
}
