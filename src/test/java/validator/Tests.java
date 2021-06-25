package validator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import silverchain.validator.ValidationError;

final class Tests {

  @Test
  void testJavaTypeReferenceConflict() {
    testJava("Foo { foo() Bar; foo() Baz; }", "Conflict: Bar#L1C13, Baz#L1C24");
  }

  @Test
  void testJavaTypeReferenceMethodConflict() {
    testJava("Foo { foo()* Bar; }", "Conflict: Bar#L1C14, foo()#L1C7");
  }

  @Test
  void testJavaMethodConflict() {
    testJava("Foo<T,S> { foo(T t) | foo(S s) Bar; }", "Conflict: foo(S s)#L1C23, foo(T t)#L1C12");
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
