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
    return tail().map(t -> join(g, t.graph())).orElse(g);
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    head().resolveReferences(parameters);
    tail().ifPresent(t -> t.resolveReferences(parameters));
  }

  @Override
  String separator() {
    return " ";
  }
}
