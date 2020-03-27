package foo;

@SuppressWarnings({"rawtypes", "unchecked"})
class Foo1<T, S> implements foo.state1.Foo<T, S> {

  foo.IFooAction action;

  Foo1(foo.IFooAction action) {
    this.action = action;
  }

  @Override
  public bar.Bar<T, S> bar(S s) {
    return this.action.state1$bar(s);
  }
}
