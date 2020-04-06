package generator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import silverchain.generator.EncodeError;
import silverchain.generator.GeneratedFile;

final class ErrorTests {

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
    test(Utility::generateJava, text, message);
  }

  private void test(Function<InputStream, List<GeneratedFile>> f, String text, String message) {
    try {
      f.apply(new ByteArrayInputStream(text.getBytes()));
      assert false;
    } catch (EncodeError e) {
      assert e.getMessage().equals(message);
    }
  }
}
