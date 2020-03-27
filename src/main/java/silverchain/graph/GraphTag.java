package silverchain.graph;

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
}
