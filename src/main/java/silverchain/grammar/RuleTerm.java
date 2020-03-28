package silverchain.grammar;

import java.util.stream.Collectors;

public final class RuleTerm extends ASTNodeN<RuleFactor, RuleTerm> {

  public RuleTerm(RuleFactor head, RuleTerm tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    return stream().map(RuleFactor::toString).collect(Collectors.joining(" "));
  }
}
