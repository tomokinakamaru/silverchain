import static utility.FileDeleter.delete;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import silverchain.command.EntryPoint;
import silverchain.parser.ParseException;

public class EntryPointTest {

  private static final Path buildGradle = Paths.get("build.gradle");

  private static final Path resources = Paths.get("src").resolve("test").resolve("resources");

  private static final Path outputDirectory = Paths.get("build").resolve("silverchain");

  private static final String helpMessage =
      "usage: silverchain [options]\n"
          + "\n"
          + "options:\n"
          + "  -h, --help           show this message and exit\n"
          + "  -v, --version        show version and exit\n"
          + "  -i, --input <path>   input grammar file\n"
          + "  -o, --output <path>  output directory\n";

  @Test
  void test1() throws IOException, ParseException {
    InputStream stream = new ByteArrayInputStream("Foo: foo() Foo;".getBytes());
    System.setIn(stream);
    EntryPoint.run("--output", outputDirectory.toString());
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
  void test3() throws IOException, ParseException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setErr(printStream);
    assert EntryPoint.run("-foo") == 1;
    assert outputStream.toString().equals("error: unknown option -foo\n" + helpMessage);
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

  @Test
  void test6() throws IOException, ParseException {
    String version =
        Files.readAllLines(buildGradle)
            .stream()
            .filter(s -> s.startsWith("version "))
            .map(s -> s.split(" ")[1].replaceAll("'", "").trim())
            .findFirst()
            .orElse(null);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);
    EntryPoint.run("-v");
    assert outputStream.toString().equals(version + "\n");
  }

  @BeforeEach
  void clean() {
    delete(outputDirectory);
  }
}
