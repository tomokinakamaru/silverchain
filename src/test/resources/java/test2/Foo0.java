@SuppressWarnings({"rawtypes", "unchecked"})
class Foo0<T> implements IFoo<T> {

  IFooAction action;

  Foo0(IFooAction action) {
    this.action = action;
  }

  @Override
  public state1.Foo<T> foo(String s, T t) {
    this.action.state0$foo(s, t);
    return new Foo1(this.action);
  }
}