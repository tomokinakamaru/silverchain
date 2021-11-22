package javadoctest.intermediates;

public interface JavadocTest0 {

  /**
   * Test imported {@link java.util.List}, {@link java.util.Map}.
   *
   * @param list
   * @param map
   */
  void test1(java.util.List<String> list, java.util.Map<String, String> map);

  /**
   * Test java.lang {@link javadoctest.Foo}.
   *
   * @param foo
   */
  void test2(javadoctest.Foo foo);

  /**
   * Test inner class {@link javadoctest.Foo.Bar}, {@link javadoctest.Foo.Bar}.
   *
   * @param bar
   * @param fooBar
   */
  void test3(javadoctest.Foo.Bar bar, javadoctest.Foo.Bar fooBar);

  /**
   * Test inner class {@link String} for state 0.
   *
   * @param str
   */
  javadoctest.intermediates.JavadocTest1 test4(String str);
}
