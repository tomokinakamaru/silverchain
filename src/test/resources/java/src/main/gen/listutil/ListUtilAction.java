package listutil;

interface ListUtilAction<T> {

  default void state0$copy() {
    copy();
  }

  default <S> void state0$copy(java.util.List<S> src, java.util.List<? super S> dst) {
    copy(src, dst);
  }

  default void state0$save(java.util.List<?> list) throws java.io.IOException {
    save(list);
  }

  default String[] state0$toArray(java.util.List<String> lst) {
    return toArray(lst);
  }

  default java.util.List<Object> state0$toList(Object... args) {
    return toList(args);
  }

  default java.util.List<String> state0$toList(String[] arr) {
    return toList(arr);
  }

  default void state1$from(java.util.List<T> src) {
    from(src);
  }

  default void state2$to(java.util.List<? super T> dst) {
    to(dst);
  }

  void copy();

  <S> void copy(java.util.List<S> src, java.util.List<? super S> dst);

  void save(java.util.List<?> list) throws java.io.IOException;

  String[] toArray(java.util.List<String> lst);

  java.util.List<Object> toList(Object... args);

  java.util.List<String> toList(String[] arr);

  void from(java.util.List<T> src);

  void to(java.util.List<? super T> dst);
}
