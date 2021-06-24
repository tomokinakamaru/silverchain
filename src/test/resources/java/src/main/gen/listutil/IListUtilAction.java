package listutil;

interface IListUtilAction<T> {

  default void state0$copy() {
    copy();
  }

  default void state1$from(java.util.List<T> src) {
    from(src);
  }

  default void state2$to(java.util.List<? super T> dst) {
    to(dst);
  }

  void copy();

  void from(java.util.List<T> src);

  void to(java.util.List<? super T> dst);
}
