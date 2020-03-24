package silverchain.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import silverchain.grammar.ASTNode;
import silverchain.grammar.QualifiedName;
import silverchain.grammar.Type;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeReference;
import silverchain.grammar.Visitor;

final class ParamRefCollector extends Visitor {

  private final Set<TypeParameter> definedParameters;

  private final List<TypeParameter> parameters = new ArrayList<>();

  ParamRefCollector(Set<TypeParameter> definedParameters) {
    this.definedParameters = definedParameters;
  }

  List<TypeParameter> apply(ASTNode node) {
    node.accept(this);
    return parameters;
  }

  @Override
  protected void visit(Type node) {
    if (node.parameters() != null && node.parameters().publicList() != null) {
      parameters.addAll(node.parameters().publicList().toList());
    }
  }

  @Override
  protected void visit(TypeReference node) {
    QualifiedName name = node.name();
    if (name.qualifier() == null) {
      definedParameters
          .stream()
          .filter(p -> p.name().equals(name.name()))
          .findFirst()
          .ifPresent(parameters::add);
    }
  }
}
