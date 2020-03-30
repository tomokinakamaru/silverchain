package silverchain.grammar;

public final class Grammars extends ASTNodeN<Grammar, Grammars> {

  public Grammars(Grammar head, Grammars tail) {
    super(head, tail);
  }

  @Override
  String separator() {
    return " ";
  }
}
