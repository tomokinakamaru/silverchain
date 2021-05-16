package validator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import silverchain.validator.ValidationError;

final class Tests {

  @Test
  void testJavaTypeReferenceConflict() {
    testJava("Foo: foo() Bar; foo() Baz;", "Conflict: Bar#L1C12, Baz#L1C23");
  }

  @Test
  void testJavaTypeReferenceMethodConflict() {
    testJava("Foo: foo()* Bar;", "Conflict: Bar#L1C13, foo()#L1C6");
  }

  @Test
  void testJavaMethodConflict() {
    testJava("Foo[T,S]: foo(T t) | foo(S s) Bar;", "Conflict: foo(S s)#L1C22, foo(T t)#L1C11");
  }

  private void testJava(String text, String message) {
    test(Utility::validateJava, text, message);
  }

  private void test(Consumer<InputStream> f, String text, String message) {
    try {
      f.accept(new ByteArrayInputStream(text.getBytes()));
      assert false;
    } catch (ValidationError e) {
      assert e.getMessage().equals(message);
    }
  }
}
