package mapbuilder;

interface IMapBuilder {

  <K, V> java.util.Map<K, V> build();

  void print();

  <K, V> mapbuilder.state1.MapBuilder<K, V> put(K key, V value);
}
