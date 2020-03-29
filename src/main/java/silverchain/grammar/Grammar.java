package silverchain.grammar;

import static silverchain.graph.GraphBuilders.join;

import java.util.List;
import java.util.Set;
import silverchain.graph.Graph;

public final class Grammar extends ASTNode2<Type, Rules> {

  public Grammar(Type type, Rules rules) {
    super(type, rules);
  }

  public Type type() {
    return left();
  }

  public Rules rules() {
    return right();
  }

  @Override
  public String toString() {
    String s = rules() == null ? "" : " " + rules().toString();
    return type().toString() + ":" + s;
  }

  public Graph graph() {
    Graph g = type().graph();
    return rules() == null ? g : join(g, rules().graph());
  }

  public List<TypeParameter> typeParameters() {
    return type().typeParameters();
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (type() != null) {
      type().resolveReferences(parameters);
    }
    if (rules() != null) {
      rules().resolveReferences(parameters);
    }
  }
}
