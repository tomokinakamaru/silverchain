package silverchain.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import silverchain.grammar.ASTNode;
import silverchain.grammar.Type;
import silverchain.grammar.TypeParameterList;
import silverchain.grammar.TypeReference;
import silverchain.grammar.Visitor;

final class ParamRefCollector extends Visitor {

  private final Set<String> definedParameters;

  private final List<String> parameters = new ArrayList<>();

  ParamRefCollector(Set<String> definedParameters) {
    this.definedParameters = definedParameters;
  }

  List<String> apply(ASTNode node) {
    node.accept(this);
    return parameters;
  }

  @Override
  protected void visit(Type node) {
    if (node.parameters() != null) {
      TypeParameterList list = node.parameters().publicList();
      while (list != null) {
        parameters.add(list.head().name());
        list = list.tail();
      }
    }
  }

  @Override
  protected void visit(TypeReference node) {
    String name = node.name().toString();
    if (definedParameters.contains(name)) {
      parameters.add(name);
    }
  }
}
