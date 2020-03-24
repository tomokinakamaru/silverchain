package silverchain.grammar;

import java.util.stream.Collectors;

public final class Rules extends ASTNodeN<Rule, Rules> {

  public Rules(Rule head, Rules tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    return toList().stream().map(Rule::toString).collect(Collectors.joining(" "));
  }
}
