@SuppressWarnings({"rawtypes", "unchecked"})
class Foo0 implements IFoo {

  IFooAction action;

  Foo0(IFooAction action) {
    this.action = action;
  }

  @Override
  public state1.Foo foo(String s, String t) {
    this.action.state0$foo(s, t);
    return new Foo1(this.action);
  }
}
