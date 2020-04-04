package silverchain.parser;

import java.util.Optional;
import silverchain.graph.Graph;

public final class Grammar extends ASTNode2<Type, Rules> {

  public Grammar(Range range, Type type, Rules rules) {
    super(range, type, rules);
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

  @Override
  public Graph graph() {
    resolveReferences(typeParameters());
    return super.graph();
  }
}
