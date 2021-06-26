package listutil;

interface IListUtilAction<T> {

  default void state0$copy() {
    copy();
  }

  default <S> void state0$copy(java.util.List<S> src, java.util.List<? super S> dst) {
    copy(src, dst);
  }

  default void state1$from(java.util.List<T> src) {
    from(src);
  }

  default void state2$to(java.util.List<? super T> dst) {
    to(dst);
  }

  void copy();

  <S> void copy(java.util.List<S> src, java.util.List<? super S> dst);

  void from(java.util.List<T> src);

  void to(java.util.List<? super T> dst);
}
