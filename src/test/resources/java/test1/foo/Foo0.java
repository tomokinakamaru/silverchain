package foo;

@SuppressWarnings({"rawtypes", "unchecked"})
class Foo0<T, S> implements foo.IFoo<T, S> {

  foo.IFooAction action;

  Foo0(foo.IFooAction action) {
    this.action = action;
  }

  @Override
  public <U> bar.Bar<U, U> baz(U u) {
    return this.action.state0$baz(u);
  }

  @Override
  public foo.state1.Foo<T, S> foo(T t) {
    this.action.state0$foo(t);
    return new foo.Foo1(this.action);
  }
}
