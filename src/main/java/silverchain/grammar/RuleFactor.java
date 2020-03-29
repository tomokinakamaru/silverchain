package silverchain.grammar;

import static silverchain.graph.GraphBuilders.repeat;

import java.util.Set;
import silverchain.graph.Graph;

public final class RuleFactor extends ASTNode2<RuleElement, RepeatOperator> {

  public RuleFactor(RuleElement element, RepeatOperator operator) {
    super(element, operator);
  }

  public RuleElement element() {
    return left();
  }

  public RepeatOperator operator() {
    return right();
  }

  @Override
  public String toString() {
    String s = operator() == null ? "" : operator().toString();
    return element().toString() + s;
  }

  public Graph graph() {
    Graph g = element().graph();
    return operator() == null ? g : repeat(g, operator().min(), operator().max());
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (element() != null) {
      element().resolveReferences(parameters);
    }
  }
}
