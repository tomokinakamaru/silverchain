package silverchain.parser;

import static silverchain.diagram.Builders.merge;

import silverchain.diagram.Diagram;

public final class Rules extends ASTNodeN<Rule, Rules> {

  public Rules(Range range, Rule head, Rules tail) {
    super(range, head, tail);
  }

  @Override
  Diagram reduce(Diagram diagram1, Diagram diagram2) {
    return merge(diagram1, diagram2);
  }

  @Override
  String separator() {
    return " ";
  }
}
