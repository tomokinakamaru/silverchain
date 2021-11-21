package javadoctest;

interface JavadocTestAction {

  default void state0$test1(java.util.List<String> list, java.util.Map<String, String> map) {
    test1(list, map);
  }

  default void state0$test2(javadoctest.Foo foo) {
    test2(foo);
  }

  default void state0$test3(javadoctest.Foo.Bar bar, javadoctest.Foo.Bar fooBar) {
    test3(bar, fooBar);
  }

  default void state0$test4(String str) {
    test4(str);
  }

  default void state1$test4(String str) {
    test4(str);
  }

  void test1(java.util.List<String> list, java.util.Map<String, String> map);

  void test2(javadoctest.Foo foo);

  void test3(javadoctest.Foo.Bar bar, javadoctest.Foo.Bar fooBar);

  void test4(String str);
}
