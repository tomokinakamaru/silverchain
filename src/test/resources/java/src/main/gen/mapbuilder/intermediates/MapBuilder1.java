package mapbuilder.intermediates;

public interface MapBuilder1<K, V> {

  java.util.Map<K, V> build();

  void print();

  mapbuilder.intermediates.MapBuilder1<K, V> put(K key, V value);
}
