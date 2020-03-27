@SuppressWarnings({"rawtypes", "unchecked"})
class Foo1<T> implements state1.Foo<T> {

  IFooAction action;

  Foo1(IFooAction action) {
    this.action = action;
  }

  @Override
  public void bar() {
    this.action.state1$bar();
  }
}
