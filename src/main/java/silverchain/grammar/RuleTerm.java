package silverchain.grammar;

import static silverchain.graph.GraphBuilders.join;

import java.util.Set;
import silverchain.graph.Graph;

public final class RuleTerm extends ASTNodeN<RuleFactor, RuleTerm> {

  public RuleTerm(RuleFactor head, RuleTerm tail) {
    super(head, tail);
  }

  public Graph graph() {
    Graph g = head().graph();
    return tail() == null ? g : join(g, tail().graph());
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
    return " ";
  }
}
