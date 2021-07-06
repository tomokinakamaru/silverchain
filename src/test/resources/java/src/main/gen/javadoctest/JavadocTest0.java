package javadoctest;

@SuppressWarnings({"rawtypes", "unchecked"})
class JavadocTest0 implements javadoctest.IJavadocTest {

  javadoctest.IJavadocTestAction action;

  JavadocTest0(javadoctest.IJavadocTestAction action) {
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
  public javadoctest.state1.JavadocTest test4(String str) {
    this.action.state0$test4(str);
    return new javadoctest.JavadocTest1(this.action);
  }
}
