package validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import silverchain.ValidationError;

final class Tests {

  @Test
  void testJavaTypeReferenceMethodConflict() throws IOException {
    testJava("Foo { Bar foo()*; }", "Conflict: Bar#L1C7, foo()#L1C11");
  }

  private void testJava(String text, String message) throws IOException {
    test(Utility::validateJava, text, message);
  }

  private void test(Consumer<InputStream> f, String text, String message) throws IOException {
    try (ByteArrayInputStream stream = new ByteArrayInputStream(text.getBytes())) {
      assertThatThrownBy(() -> f.accept(stream))
          .isExactlyInstanceOf(ValidationError.class)
          .hasMessage(message);
    }
  }
}
