interface IFooAction<T> {

  default void state0$foo(String s, T t) {
    foo(s, t);
  }

  default void state1$bar() {
    bar();
  }

  void foo(String s, T t);

  void bar();
}
