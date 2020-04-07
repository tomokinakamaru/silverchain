package generator;

import static generator.Utility.generateJava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import silverchain.generator.GeneratedFile;

final class Tests {

  private final Path resources = Paths.get("src").resolve("test").resolve("resources");

  @Test
  void testAlertDialog() {
    test("alertdialog");
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

  private void test(String name) {
    Path path = resources.resolve(name + ".ag");
    List<GeneratedFile> generated = generateJava(Utility.read(path));
    List<GeneratedFile> expected = expectedJavaFiles(name);
    assert generated.size() == expected.size();
    for (GeneratedFile file : generated) {
      assert expected.stream().anyMatch(f -> equals(f, file));
    }
  }

  private boolean equals(GeneratedFile file1, GeneratedFile file2) {
    return file1.path().equals(file2.path()) && file1.content().equals(file2.content());
  }

  private List<GeneratedFile> expectedJavaFiles(String name) {
    Path base = resources.resolve("java");
    try {
      return Files.walk(base.resolve(name))
          .filter(p -> p.toString().endsWith(".java"))
          .map(p -> new GeneratedFile(base.relativize(p), read(p)))
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
