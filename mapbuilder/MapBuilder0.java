package mapbuilder;

@SuppressWarnings({"rawtypes", "unchecked"})
class MapBuilder0 implements mapbuilder.IMapBuilder {

  mapbuilder.IMapBuilderAction action;

  MapBuilder0(mapbuilder.IMapBuilderAction action) {
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
  public <K, V> mapbuilder.state1.MapBuilder<K, V> put(K key, V value) {
    this.action.state0$put(key, value);
    return new mapbuilder.MapBuilder1(this.action);
  }
}
