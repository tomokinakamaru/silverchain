package silverchain.analyzer;

import java.util.HashSet;
import java.util.Set;
import silverchain.grammar.Grammar;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.Visitor;

final class ParamDefCollector extends Visitor {

  private final Set<TypeParameter> parameters = new HashSet<>();

  Set<TypeParameter> apply(Grammar grammar) {
    grammar.accept(this);
    return parameters;
  }

  @Override
  protected void visit(TypeParameter node) {
    parameters.add(node);
  }
}
