@SuppressWarnings({"rawtypes", "unchecked"})
class Foo1 implements state1.Foo {

  IFooAction action;

  Foo1(IFooAction action) {
    this.action = action;
  }

  @Override
  public void bar() {
    this.action.state1$bar();
  }
}
