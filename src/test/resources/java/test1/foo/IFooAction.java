package foo;

interface IFooAction<T, S, U> {

  default bar.Bar<U, U> state0$baz(U u) {
    return baz(u);
  }

  default void state0$foo(T t) {
    foo(t);
  }

  default bar.Bar<T, S> state1$bar(S s) {
    return bar(s);
  }

  bar.Bar<U, U> baz(U u);

  void foo(T t);

  bar.Bar<T, S> bar(S s);
}
