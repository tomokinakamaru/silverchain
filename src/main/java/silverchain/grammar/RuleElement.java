package silverchain.grammar;

import java.util.Set;
import silverchain.graph.Graph;

public final class RuleElement extends ASTNode2<Method, RuleExpression> {

  public RuleElement(Method method, RuleExpression expression) {
    super(method, expression);
  }

  public Method method() {
    return left();
  }

  public RuleExpression expression() {
    return right();
  }

  @Override
  public String toString() {
    if (method() == null) {
      return "(" + expression().toString() + ")";
    }
    return method().toString();
  }

  public Graph graph() {
    return method() == null ? expression().graph() : method().graph();
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (method() != null) {
      method().resolveReferences(parameters);
    }
    if (expression() != null) {
      expression().resolveReferences(parameters);
    }
  }
}
