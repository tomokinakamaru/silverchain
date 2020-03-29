package silverchain.grammar;

import static silverchain.graph.GraphBuilders.merge;

import java.util.Set;
import silverchain.graph.Graph;

public final class Rules extends ASTNodeN<Rule, Rules> {

  public Rules(Rule head, Rules tail) {
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
    return " ";
  }
}
