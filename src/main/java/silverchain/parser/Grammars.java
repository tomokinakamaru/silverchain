package silverchain.parser;

public final class Grammars extends ASTNodeN<Grammar, Grammars> {

  Grammars(Range range, Grammar head, Grammars tail) {
    super(range, head, tail);
  }
}
