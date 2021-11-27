package generator;

import static generator.Utility.generateJava;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import silverchain.generator.File;

final class Tests {

  private final Path resources = Paths.get("src").resolve("test").resolve("resources");

  @Test
  void testAlertDialog() {
    test("alertdialog");
  }

  @Test
  void testJavadocTest() {
    test("javadoctest");
  }

  @Test
  void testListUtil() {
    test("listutil");
  }

  @Test
  void testMapBuilder() {
    test("mapbuilder");
  }

  @Test
  void testMatrix() {
    test("matrix");
  }

  @Test
  void testMelody() {
    test("melody");
  }

  @Test
  void testTripletBuilder() {
    test("tripletbuilder");
  }

  @Test
  void testItemization() {
    test("itemization");
  }

  private void test(String name) {
    Path path = resources.resolve(name + ".ag");
    String javadoc = "src/test/resources/java/src/main";
    List<File> generated = generateJava(Utility.read(path), javadoc);
    List<File> expected = expectedJavaFiles(name);
    assertThat(generated.size()).isEqualTo(expected.size());
    for (File file : generated) {
      assertThat(expected).anyMatch(f -> equals(f, file));
    }
  }

  private boolean equals(File file1, File file2) {
    return file1.path().equals(file2.path()) && file1.content().equals(file2.content());
  }

  private List<File> expectedJavaFiles(String name) {
    Path base = resources.resolve("java").resolve("src").resolve("main").resolve("gen");
    try {
      return Files.walk(base.resolve(name))
          .filter(p -> p.toString().endsWith(".java"))
          .map(p -> new File(base.relativize(p), read(p)))
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String read(Path path) {
    try {
      return new String(Files.readAllBytes(path));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
