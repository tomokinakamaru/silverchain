package silverchain.grammar;

import java.util.Objects;

public abstract class ASTNode implements Comparable<ASTNode> {

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
  public int hashCode() {
    return Objects.hash(toString());
  }

  @Override
  public final int compareTo(ASTNode node) {
    return toString().compareTo(node.toString());
  }
}
