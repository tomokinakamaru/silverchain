package silverchain.grammar;

import static silverchain.graph.GraphBuilders.join;

import java.util.Set;
import silverchain.graph.Graph;

public final class Rule extends ASTNode2<RuleExpression, TypeReference> {

  public Rule(RuleExpression expression, TypeReference type) {
    super(expression, type);
  }

  public RuleExpression expression() {
    return left();
  }

  public TypeReference type() {
    return right();
  }

  @Override
  public String toString() {
    String s = type() == null ? "" : " " + type().toString();
    return expression().toString() + s + ";";
  }

  public Graph graph() {
    Graph g = expression().graph();
    return type() == null ? g : join(g, type().graph());
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (expression() != null) {
      expression().resolveReferences(parameters);
    }
    if (type() != null) {
      type().resolveReferences(parameters);
    }
  }
}
