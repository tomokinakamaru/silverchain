package silverchain.grammar;

import java.util.List;
import java.util.Objects;
import silverchain.graph.Graph;

public abstract class ASTNode implements Comparable<ASTNode> {

  public abstract List<TypeParameter> typeParameters();

  public abstract void resolveReferences(List<TypeParameter> typeParameters);

  public abstract Graph graph();

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
