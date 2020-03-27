import static org.junit.jupiter.api.Assertions.assertThrows;
import static utility.FileDeleter.delete;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import silverchain.EntryPoint;
import silverchain.parser.ParseException;

public class EntryPointTest {

  private static final Path resources = Paths.get("src").resolve("test").resolve("resources");

  private static final Path outputDirectory = Paths.get("build").resolve("silverchain");

  private static final String helpMessage =
      "usage: silverchain [-h] [-i <path>] [-o <path>]\n"
          + "\n"
          + "optional arguments:\n"
          + "  -h, --help  show this help message and exit\n"
          + "  -i <path>   specify input file\n"
          + "  -o <path>   specify output directory\n";

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

  @Test
  void test4() throws IOException, ParseException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    EntryPoint.run("-h");
    assert outputStream.toString().equals(helpMessage);
  }

  @Test
  void test5() throws IOException, ParseException {
    EntryPoint.run(
        "-i",
        resources.resolve("java").resolve("test1.ag").toString(),
        "-o",
        outputDirectory.toString());
  }

  @BeforeEach
  void clean() {
    delete(outputDirectory);
  }
}
