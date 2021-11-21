package silverchain.parser;

import static silverchain.diagram.Builders.atom;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import silverchain.diagram.Diagram;

public final class Method extends ASTNode2<String, MethodParameters> {

  private final TypeReferences exceptions;

  public Method(Range range, String name, MethodParameters parameters, TypeReferences exceptions) {
    super(range, name, parameters);
    this.exceptions = exceptions;
  }

  public String name() {
    return left();
  }

  public MethodParameters parameters() {
    return right();
  }

  public Optional<TypeReferences> exceptions() {
    return Optional.ofNullable(exceptions);
  }

  @Override
  public String toString() {
    String s1 = name() + parameters();
    String s2 = exceptions().map(es -> " throws " + es).orElse("");
    return s1 + s2;
  }

  @Override
  public Diagram diagram(Map<String, QualifiedName> importMap) {
    exceptions().ifPresent(e -> e.resolveReferences(new ArrayList<>(), importMap));
    return atom(this);
  }
}
