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
    return tail().map(t -> merge(g, t.graph())).orElse(g);
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    head().resolveReferences(parameters);
    tail().ifPresent(t -> t.resolveReferences(parameters));
  }

  @Override
  String separator() {
    return "|";
  }
}
