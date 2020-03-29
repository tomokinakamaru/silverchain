package silverchain.grammar;

import static silverchain.graph.GraphBuilders.merge;

import java.util.Set;
import silverchain.graph.Graph;

public final class RuleExpression extends ASTNodeN<RuleTerm, RuleExpression> {

  public RuleExpression(RuleTerm head, RuleExpression tail) {
    super(head, tail);
  }

  public Graph graph() {
    Graph g = head().graph();
    return tail() == null ? g : merge(g, tail().graph());
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (head() != null) {
      head().resolveReferences(parameters);
    }
    if (tail() != null) {
      tail().resolveReferences(parameters);
    }
  }

  @Override
  String separator() {
    return "|";
  }
}
