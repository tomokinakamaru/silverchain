package silverchain.graph;

public final class GraphLabel {

  final Object object;

  GraphLabel(Object object) {
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
}
