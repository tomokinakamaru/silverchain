public class Foo implements IFoo {
  @Override
  public state1.Foo foo(String s, String t){
    FooListener listener = new FooListener();
    listener.foo(s, t);
    return new State1(listener);
  }
}
