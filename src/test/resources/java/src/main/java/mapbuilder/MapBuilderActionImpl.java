package mapbuilder;

import java.util.HashMap;
import java.util.Map;

final class MapBuilderActionImpl<K, V> implements MapBuilderAction<K, V> {

  private final Map<K, V> map = new HashMap<>();

  @Override
  public Map<K, V> build() {
    return map;
  }

  @Override
  public void print() {}

  @Override
  public void put(K key, V value) {
    map.put(key, value);
  }
}
