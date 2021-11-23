package mapbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class MapBuilder0Impl implements mapbuilder.intermediates.MapBuilder0 {

  mapbuilder.MapBuilderAction action;

  MapBuilder0Impl(mapbuilder.MapBuilderAction action) {
    this.action = action;
  }

  @Override
  public <K, V> java.util.Map<K, V> build() {
    return this.action.state0$build();
  }

  @Override
  public void print() {
    this.action.state0$print();
  }

  @Override
  public <K, V> mapbuilder.intermediates.MapBuilder1<K, V> put(K key, V value) {
    this.action.state0$put(key, value);
    return new mapbuilder.MapBuilder1Impl(this.action);
  }
}
