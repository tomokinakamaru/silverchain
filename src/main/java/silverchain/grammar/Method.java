package silverchain.grammar;

import static silverchain.graph.GraphBuilders.atom;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import silverchain.graph.Graph;

public final class Method extends ASTNode2<String, MethodParameters> {

  public Method(String name, MethodParameters parameters) {
    super(name, parameters);
  }

  public String name() {
    return left();
  }

  public MethodParameters parameters() {
    return right();
  }

  public List<TypeParameter> referents() {
    return parameters() == null ? Collections.emptyList() : parameters().referents();
  }

  @Override
  public String toString() {
    String s = parameters() == null ? "" : parameters().toString();
    return name() + "(" + s + ")";
  }

  public Graph graph() {
    return atom(this);
  }

  public void resolveReferences(Set<TypeParameter> parameters) {
    if (parameters() != null) {
      parameters().resolveReferences(parameters);
    }
  }
}
