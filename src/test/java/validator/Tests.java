package validator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import silverchain.validator.ValidationError;

final class Tests {

  @Test
  void testJavaTypeReferenceMethodConflict() {
    testJava("Foo { Bar foo()*; }", "Conflict: Bar#L1C7, foo()#L1C11");
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
