package validator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import silverchain.validator.ValidationError;

final class Tests {

  @Test
  void testJavaTypeReferenceConflict() {
    testJava("Foo { Bar foo(); Baz foo(); }", "Conflict: Bar#L1C7, Baz#L1C18");
  }

  @Test
  void testJavaTypeReferenceMethodConflict() {
    testJava("Foo { Bar foo()*; }", "Conflict: Bar#L1C7, foo()#L1C11");
  }

  @Test
  void testJavaMethodConflict() {
    testJava("Foo<T,S> { Bar foo(T t) | foo(S s); }", "Conflict: foo(S s)#L1C27, foo(T t)#L1C16");
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
