package silverchain.analyzer;

import java.util.HashSet;
import java.util.Set;
import silverchain.grammar.Grammar;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeReference;
import silverchain.grammar.Visitor;

final class ReferenceResolver extends Visitor {

  private final Set<TypeParameter> parameters = new HashSet<>();

  void apply(Grammar grammar) {
    grammar.accept(this);
  }

  @Override
  protected void visit(TypeParameter node) {
    parameters.add(node);
  }

  @Override
  protected void visit(TypeReference node) {
    if (node.name().qualifier() == null) {
      String name = node.name().name();
      node.referent(findParameter(name));
    }
  }

  private TypeParameter findParameter(String name) {
    return parameters.stream().filter(p -> p.name().equals(name)).findFirst().orElse(null);
  }
}
