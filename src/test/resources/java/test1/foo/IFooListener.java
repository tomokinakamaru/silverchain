package foo;

interface IFooListener<T, S, U> {
  bar.Bar<U, U> baz(U u);

  void foo(T t);

  bar.Bar<T, S> bar(S s);
}
