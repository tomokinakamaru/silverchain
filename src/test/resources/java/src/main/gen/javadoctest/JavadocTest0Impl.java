package javadoctest;

@SuppressWarnings({"rawtypes", "unchecked"})
class JavadocTest0Impl implements javadoctest.intermediates.JavadocTest0 {

  javadoctest.JavadocTestAction action;

  JavadocTest0Impl(javadoctest.JavadocTestAction action) {
    this.action = action;
  }

  @Override
  public void test1(java.util.List<String> list, java.util.Map<String, String> map) {
    this.action.state0$test1(list, map);
  }

  @Override
  public void test2(javadoctest.Foo foo) {
    this.action.state0$test2(foo);
  }

  @Override
  public void test3(javadoctest.Foo.Bar bar, javadoctest.Foo.Bar fooBar) {
    this.action.state0$test3(bar, fooBar);
  }

  @Override
  public javadoctest.intermediates.JavadocTest1 test4(String str) {
    this.action.state0$test4(str);
    return new javadoctest.JavadocTest1Impl(this.action);
  }
}
