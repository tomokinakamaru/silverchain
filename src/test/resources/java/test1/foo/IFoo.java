package foo;

interface IFoo<T, S> {
  <U> bar.Bar<U, U> baz(U u);

  foo.state1.Foo<T, S> foo(T t);
}
