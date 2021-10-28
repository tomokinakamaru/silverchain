package silverchain.parser;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import silverchain.diagram.Diagram;

public abstract class ASTNode implements Comparable<ASTNode> {

  private final Range range;

  public abstract List<TypeParameter> typeParameters();

  public abstract Diagram diagram(Map<String, QualifiedName> importMap);

  public abstract List<TypeParameter> referents();

  public abstract void validate();

  abstract void resolveReferences(
      List<TypeParameter> typeParameters, Map<String, QualifiedName> importMap);

  ASTNode(Range range) {
    this.range = range;
  }

  public final Range range() {
    return range;
  }

  @Override
  public final boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    return toString().equals(obj.toString());
  }

  @Override
  public final int hashCode() {
    return Objects.hash(toString());
  }

  @Override
  public final int compareTo(ASTNode node) {
    return toString().compareTo(node.toString());
  }
}
