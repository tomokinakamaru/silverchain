package javadoctest;

@SuppressWarnings({"rawtypes", "unchecked"})
class JavadocTest1Impl implements javadoctest.intermediates.JavadocTest1 {

  javadoctest.JavadocTestAction action;

  JavadocTest1Impl(javadoctest.JavadocTestAction action) {
    this.action = action;
  }

  @Override
  public void test4(String str) {
    this.action.state1$test4(str);
  }
}
