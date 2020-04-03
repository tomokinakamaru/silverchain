package silverchain.grammar;

public final class Grammars extends ASTNodeN<Grammar, Grammars> {

  public Grammars(Range range, Grammar head, Grammars tail) {
    super(range, head, tail);
  }

  @Override
  String separator() {
    return " ";
  }
}
