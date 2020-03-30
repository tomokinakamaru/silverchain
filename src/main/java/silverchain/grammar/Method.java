package silverchain.grammar;

import static silverchain.graph.GraphBuilders.atom;

import java.util.Optional;
import silverchain.graph.Graph;

public final class Method extends ASTNode2<String, MethodParameters> {

  public Method(String name, MethodParameters parameters) {
    super(name, parameters);
  }

  public String name() {
    return left();
  }

  public Optional<MethodParameters> parameters() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return name() + "(" + parameters().map(ASTNodeN::toString).orElse("") + ")";
  }

  public Graph graph() {
    return atom(this);
  }
}
