package silverchain.grammar;

import java.util.stream.Collectors;

public final class Grammars extends ASTNodeN<Grammar, Grammars> {

  public Grammars(Grammar head, Grammars tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    return stream().map(Grammar::toString).collect(Collectors.joining(" "));
  }
}
