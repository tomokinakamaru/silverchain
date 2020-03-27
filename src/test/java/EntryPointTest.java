import static org.junit.jupiter.api.Assertions.assertThrows;
import static utility.FileDeleter.delete;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import silverchain.EntryPoint;
import silverchain.parser.ParseException;

public class EntryPointTest {

  private static final Path outputDirectory = Paths.get("build").resolve("silverchain");

  @Test
  void test1() throws IOException, ParseException {
    InputStream stream = new ByteArrayInputStream("Foo: foo() Foo;".getBytes());
    System.setIn(stream);
    EntryPoint.run("-o", outputDirectory.toString());
  }

  @Test
  void test2() throws IOException, ParseException {
    InputStream stream = new ByteArrayInputStream("Foo: foo() Foo;".getBytes());
    System.setIn(stream);
    EntryPoint.run();

    File[] files = Paths.get(".").toFile().listFiles((file, name) -> name.endsWith(".java"));
    if (files != null) {
      for (File file : files) {
        file.deleteOnExit();
      }
    }
  }

  @Test
  void test3() {
    assertThrows(RuntimeException.class, () -> EntryPoint.run("-foo"));
  }

  @BeforeEach
  void clean() {
    delete(outputDirectory);
  }
}
