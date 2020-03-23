package foo;

class State1<T, S> implements foo.state1.Foo<T, S> {
  private final FooListener<T, S, U> listener;

  State1(FooListener<T, S, U> listener){
    this.listener = listener;
  }

  @Override
  public bar.Bar<T, S> bar(S s){
    return listener.bar(s);
  }
}
