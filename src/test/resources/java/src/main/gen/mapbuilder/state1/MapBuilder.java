package mapbuilder.state1;

public interface MapBuilder<K, V> {

  java.util.Map<K, V> build();

  void print();

  mapbuilder.state1.MapBuilder<K, V> put(K key, V value);
}
