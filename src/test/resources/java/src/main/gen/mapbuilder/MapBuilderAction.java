package mapbuilder;

interface MapBuilderAction<K, V> {

  default java.util.Map<K, V> state0$build() {
    return build();
  }

  default void state0$print() {
    print();
  }

  default void state0$put(K key, V value) {
    put(key, value);
  }

  default java.util.Map<K, V> state1$build() {
    return build();
  }

  default void state1$print() {
    print();
  }

  default void state1$put(K key, V value) {
    put(key, value);
  }

  java.util.Map<K, V> build();

  void print();

  void put(K key, V value);
}
