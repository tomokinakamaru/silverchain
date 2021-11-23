package tripletbuilder;

interface TripletBuilderAction<T> {

  default java.util.List<T> state0$build() {
    return build();
  }

  default void state0$setFirst(T item) {
    setFirst(item);
  }

  default void state0$setSecond(T item) {
    setSecond(item);
  }

  default void state0$setThird(T item) {
    setThird(item);
  }

  default java.util.List<T> state1$build() {
    return build();
  }

  default void state1$setSecond(T item) {
    setSecond(item);
  }

  default void state1$setThird(T item) {
    setThird(item);
  }

  default java.util.List<T> state2$build() {
    return build();
  }

  default void state2$setFirst(T item) {
    setFirst(item);
  }

  default void state2$setThird(T item) {
    setThird(item);
  }

  default java.util.List<T> state3$build() {
    return build();
  }

  default void state3$setFirst(T item) {
    setFirst(item);
  }

  default void state3$setSecond(T item) {
    setSecond(item);
  }

  default java.util.List<T> state4$build() {
    return build();
  }

  default void state4$setThird(T item) {
    setThird(item);
  }

  default java.util.List<T> state5$build() {
    return build();
  }

  default void state5$setSecond(T item) {
    setSecond(item);
  }

  default java.util.List<T> state6$build() {
    return build();
  }

  default void state6$setFirst(T item) {
    setFirst(item);
  }

  default java.util.List<T> state7$build() {
    return build();
  }

  java.util.List<T> build();

  void setFirst(T item);

  void setSecond(T item);

  void setThird(T item);
}
