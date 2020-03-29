package silverchain.grammar;

import static silverchain.graph.GraphBuilders.merge;

import java.util.Set;
import java.util.stream.Collectors;
import silverchain.graph.Graph;

public final class Rules extends ASTNodeN<Rule, Rules> {

  public Rules(Rule head, Rules tail) {
    super(head, tail);
  }

  @Override
  public String toString() {
    return stream().map(Rule::toString).collect(Collectors.joining(" "));
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
}
