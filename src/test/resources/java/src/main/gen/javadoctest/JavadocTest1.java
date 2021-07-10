package javadoctest;

@SuppressWarnings({"rawtypes", "unchecked"})
class JavadocTest1 implements javadoctest.state1.JavadocTest {

  javadoctest.IJavadocTestAction action;

  JavadocTest1(javadoctest.IJavadocTestAction action) {
    this.action = action;
  }

  @Override
  public void test4(String str) {
    this.action.state1$test4(str);
  }
}
