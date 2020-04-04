package silverchain.parser;

import java.util.Objects;
import java.util.Set;
import silverchain.graph.Graph;

public abstract class ASTNode implements Comparable<ASTNode> {

  private final Range range;

  public abstract Set<TypeParameter> typeParameters();

  public abstract Graph graph();

  public abstract Set<TypeParameter> referents();

  abstract void resolveReferences(Set<TypeParameter> typeParameters);

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
