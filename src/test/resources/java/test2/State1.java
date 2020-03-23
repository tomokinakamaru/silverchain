class State1 implements state1.Foo {
  private final FooListener listener;

  State1(FooListener listener){
    this.listener = listener;
  }

  @Override
  public void bar(){
    listener.bar();
  }
}
