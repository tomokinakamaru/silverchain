package silverchain.parser;

import java.util.List;
import java.util.Map;
import silverchain.diagram.Diagram;

public abstract class ASTNode1<T> extends ASTNode {

  private final T child;

  ASTNode1(Range range, T child) {
    super(range);
    this.child = child;
  }

  T child() {
    return child;
  }

  @Override
  public List<TypeParameter> typeParameters() {
    return null;
  }

  @Override
  public Diagram diagram(Map<String, QualifiedName> importMap) {
    return null;
  }

  @Override
  public List<TypeParameter> referents() {
    return null;
  }

  @Override
  public void validate() {}

  @Override
  void resolveReferences(
      List<TypeParameter> typeParameters, Map<String, QualifiedName> importMap) {}
}
