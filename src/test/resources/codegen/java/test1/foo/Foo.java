package foo;

public interface Foo<T, S> {

  <U> foo.state1.Bar<T, S, U> baz(U u);

  foo.state2.Foo<T, S> foo(T t);
}
