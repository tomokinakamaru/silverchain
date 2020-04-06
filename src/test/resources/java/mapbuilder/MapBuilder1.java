package mapbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class MapBuilder1<K, V> implements mapbuilder.state1.MapBuilder<K, V> {

  mapbuilder.IMapBuilderAction action;

  MapBuilder1(mapbuilder.IMapBuilderAction action) {
    this.action = action;
  }

  @Override
  public java.util.Map<K, V> build() {
    return this.action.state1$build();
  }

  @Override
  public void print() {
    this.action.state1$print();
  }

  @Override
  public mapbuilder.state1.MapBuilder<K, V> put(K key, V value) {
    this.action.state1$put(key, value);
    return new mapbuilder.MapBuilder1(this.action);
  }
}
