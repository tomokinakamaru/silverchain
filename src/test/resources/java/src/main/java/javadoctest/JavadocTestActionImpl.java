package javadoctest;

import java.util.List;
import java.util.Map;
import javadoctest.Foo.Bar;

public class JavadocTestActionImpl implements JavadocTestAction {
  /**
   * Test imported {@link List}, {@link Map}.
   *
   * @param list
   * @param map
   */
  @Override
  public void test1(List<String> list, Map<String, String> map) {}

  /**
   * Test java.lang {@link Foo}.
   *
   * @param foo
   */
  @Override
  public void test2(Foo foo) {}

  /**
   * Test inner class {@link Foo.Bar}, {@link Bar}.
   *
   * @param bar
   * @param fooBar
   */
  @Override
  public void test3(Bar bar, Foo.Bar fooBar) {}

  /**
   * Test inner class {@link String}.
   *
   * @param str
   */
  @Override
  public void test4(String str) {}

  /**
   * Test inner class {@link String} for state 0.
   *
   * @param str
   */
  @Override
  public void state0$test4(String str) {
    JavadocTestAction.super.state0$test4(str);
  }
}
