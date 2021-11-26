package command;

import static command.Tester.test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Tests {

  private static final Path resources = Paths.get("src").resolve("test").resolve("resources");

  private static final Path workspace = Paths.get("build").resolve("silverchain");

  private static final String help =
      "Usage: silverchain [-hv] [-i=<path>] [-j=<path>] [-m=<n>] [-o=<path>]\n"
          + "  -h, --help                 Show this message and exit\n"
          + "  -v, --version              Show version and exit\n"
          + "  -i, --input=<path>         Input grammar file\n"
          + "  -o, --output=<path>        Output directory\n"
          + "  -j, --javadoc=<path>       Javadoc source directory\n"
          + "  -m, --max-file-count=<n>   Max number of generated files\n";

  @Test
  void testHelp() {
    Result r1 = test("-h");
    r1.status(0);
    r1.stdout(help);
    r1.stderr("");

    Result r2 = test("--help");
    r2.status(0);
    r2.stdout(help);
    r2.stderr("");
  }

  @Test
  void testVersion() {
    String version = findVersion() + "\n";

    Result r1 = test("-v");
    r1.status(0);
    r1.stdout(version);
    r1.stderr("");

    Result r2 = test("--version");
    r2.status(0);
    r2.stdout(version);
    r2.stderr("");
  }

  @Test
  void testUnknownOption() {
    Result r = test("-foo");
    r.status(2);
    r.stdout("");
    r.stderr("Unknown option: '-foo'\n" + help);
  }

  @Test
  void testInputError1() {
    Result r1 = test("-i", "foo.ag");
    r1.status(1);
    r1.stdout("");
    r1.stderr1("java.io.FileNotFoundException: foo.ag (No such file or directory)");

    Result r2 = test("--input", "foo.ag");
    r2.status(1);
    r2.stdout("");
    r2.stderr1("java.io.FileNotFoundException: foo.ag (No such file or directory)");
  }

  @Test
  void testInputError2() {
    System.setIn(new BrokenStream("Foo {}"));
    Result r = test("-o", workspace.toString());
    r.status(1);
    r.stdout("");
    r.stderr1("java.io.IOException: -");
  }

  @Test
  void testTokenizeError() {
    input("~");
    Result r = test("-o", workspace.toString());
    r.status(1);
    r.stdout("");
  }

  @Test
  void testParseError() {
    input("{");
    Result r = test("-o", workspace.toString());
    r.status(1);
    r.stdout("");
  }

  @Test
  void testDuplicateDeclaration() {
    input("Foo<T,T> {}");
    Result r = test("-o", workspace.toString());
    r.status(1);
    r.stdout("");
    r.stderr1("silverchain.parser.DuplicateDeclaration: T is already defined (L1C7)");
  }

  @Test
  void testSaveError() {
    input("Foo { void foo(); }");
    Result r = test("-o", "build.gradle");
    r.status(1);
    r.stdout("");
    r.stderr1(
        "silverchain.generator.SaveError: Failed to save generated file: build.gradle/FooAction.java");
  }

  @Test
  void testSuccessStdin() {
    input("Foo { Bar foo(); }");
    Result r1 = test("-o", workspace.toString());
    r1.status(0);
    r1.stdout("");
    r1.stderr("");

    input("Foo { Bar foo(); }");
    Result r2 = test("--output", workspace.toString());
    r2.status(0);
    r2.stdout("");
    r2.stderr("");
  }

  @Test
  void testSuccessFile() {
    Result r = test("-i", resource("mapbuilder.ag"), "-o", workspace.toString());
    r.status(0);
    r.stdout("");
    r.stderr("");
  }

  @Test
  void testNoJavadocs() {
    Result r = test("-i", resource("mapbuilder.ag"), "-j", "x", "-o", workspace.toString());
    r.status(0);
    r.stdout("");
    r.stderr("WARNING: No javadoc comments were found in x\n");
  }

  private String resource(String name) {
    return resources.resolve(name).toString();
  }

  private void input(String text) {
    System.setIn(new ByteArrayInputStream(text.getBytes()));
  }

  private String findVersion() {
    return readGradleProperties()
        .filter(s -> s.startsWith("version="))
        .map(s -> s.split("=")[1])
        .findFirst()
        .orElse(null);
  }

  private Stream<String> readGradleProperties() {
    try {
      return Files.readAllLines(Paths.get("gradle.properties")).stream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @BeforeEach
  void renewWorkspace() {
    try {
      delete(workspace.toFile());
      Files.createDirectories(workspace);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  private static void delete(File file) {
    if (!file.exists()) {
      return;
    }
    File[] files = file.listFiles();
    if (files != null) {
      for (File f : files) {
        delete(f);
      }
    }
    file.delete();
  }
}
