package foo;

public class Foo<T, S> implements foo.IFoo<T, S> {
  @Override
  public <U> bar.Bar<U, U> baz(U u){
    FooListener<T, S, U> listener = new FooListener<>();
    return listener.baz(u);
  }

  @Override
  public foo.state1.Foo<T, S> foo(T t){
    FooListener<T, S, ?> listener = new FooListener<>();
    listener.foo(t);
    return new State1<>(listener);
  }
}
