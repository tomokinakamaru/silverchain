package silverchain.grammar;

import static silverchain.graph.GraphBuilders.join;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import silverchain.graph.Graph;

public final class Grammar extends ASTNode2<Type, Rules> {

  public Grammar(Type type, Rules rules) {
    super(type, rules);
  }

  public Type type() {
    return left();
  }

  public Optional<Rules> rules() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return type() + ":" + rules().map(r -> " " + r).orElse("");
  }

  public Graph graph() {
    Graph g = type().graph();
    return rules().map(r -> join(g, r.graph())).orElse(g);
  }

  public List<TypeParameter> typeParameters() {
    return type().typeParameters();
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    type().resolveReferences(parameters);
    rules().ifPresent(r -> r.resolveReferences(parameters));
  }
}
