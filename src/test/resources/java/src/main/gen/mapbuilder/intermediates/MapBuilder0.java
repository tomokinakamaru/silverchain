package mapbuilder.intermediates;

public interface MapBuilder0 {

  <K, V> java.util.Map<K, V> build();

  void print();

  <K, V> mapbuilder.intermediates.MapBuilder1<K, V> put(K key, V value);
}
