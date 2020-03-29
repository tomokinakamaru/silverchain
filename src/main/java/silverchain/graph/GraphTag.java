package silverchain.graph;

import java.util.Objects;

public final class GraphTag {

  final Object object;

  GraphTag(Object object) {
    this.object = object;
  }

  public Object raw() {
    return object;
  }

  public <T> T as(Class<T> clazz) {
    return clazz.cast(object);
  }

  @Override
  public boolean equals(Object object1) {
    if (this == object1) return true;
    if (object1 == null || getClass() != object1.getClass()) return false;
    GraphTag tag = (GraphTag) object1;
    return Objects.equals(object, tag.object);
  }

  @Override
  public int hashCode() {
    return Objects.hash(object);
  }
}
