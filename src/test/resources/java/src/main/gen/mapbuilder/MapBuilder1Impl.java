package mapbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class MapBuilder1Impl<K, V> implements mapbuilder.intermediates.MapBuilder1<K, V> {

  mapbuilder.MapBuilderAction action;

  MapBuilder1Impl(mapbuilder.MapBuilderAction action) {
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
  public mapbuilder.intermediates.MapBuilder1<K, V> put(K key, V value) {
    this.action.state1$put(key, value);
    return new mapbuilder.MapBuilder1Impl(this.action);
  }
}
