package silverchain.parser;

import static silverchain.diagram.Builders.atom;

import java.util.Optional;
import silverchain.diagram.Diagram;

public final class Method extends ASTNode2<String, MethodParameters> {

  Method(Range range, String name, MethodParameters parameters) {
    super(range, name, parameters);
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

  @Override
  public Diagram diagram() {
    return atom(this);
  }
}
