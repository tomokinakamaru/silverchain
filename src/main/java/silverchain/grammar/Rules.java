package silverchain.grammar;

public final class Rules extends ASTNodeN<Rule, Rules> {

  public Rules(Rule head, Rules tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    String s = tail() == null ? "" : " " + tail().toString();
    return head().toString() + s;
  }
}
