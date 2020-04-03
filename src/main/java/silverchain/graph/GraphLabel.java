package silverchain.graph;

import java.util.Objects;
import silverchain.parser.ASTNode;

public final class GraphLabel implements Comparable<GraphLabel> {

  final ASTNode object;

  GraphLabel(ASTNode object) {
    this.object = object;
  }

  public Object raw() {
    return object;
  }

  public <T> T as(Class<T> clazz) {
    return clazz.cast(object);
  }

  public <T> boolean is(Class<T> clazz) {
    return object.getClass() == clazz;
  }

  @Override
  public boolean equals(Object object1) {
    if (this == object1) return true;
    if (object1 == null || getClass() != object1.getClass()) return false;
    GraphLabel label = (GraphLabel) object1;
    return Objects.equals(object, label.object);
  }

  @Override
  public int hashCode() {
    return Objects.hash(object);
  }

  @Override
  public int compareTo(GraphLabel o) {
    return object.compareTo(o.object);
  }
}
