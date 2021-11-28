package generator;

import static generator.Utility.generateJava;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import silverchain.generator.File;

final class Tests {

  private final Path resources = Paths.get("src").resolve("test").resolve("resources");

  @ParameterizedTest
  @ValueSource(
      strings = {
        "alertdialog",
        "javadoctest",
        "listutil",
        "mapbuilder",
        "matrix",
        "melody",
        "tripletbuilder",
        "itemization"
      })
  void test(String name) {
    Path path = resources.resolve(name + ".ag");
    String javadoc = "src/test/resources/java/src/main";
    Map<Path, String> generated = toComparisonMap(generateJava(Utility.read(path), javadoc));
    Map<Path, String> expected = toComparisonMap(expectedJavaFiles(name));

    assertThat(generated.keySet()).isEqualTo(expected.keySet());
    for (Map.Entry<Path, String> generatedEntry : generated.entrySet()) {
      assertThat(generatedEntry.getValue())
          .describedAs("Contents of %s", generatedEntry.getKey())
          .isEqualTo(expected.get(generatedEntry.getKey()));
    }
  }

  private HashMap<Path, String> toComparisonMap(List<File> files) {
    HashMap<Path, String> result = new HashMap<>();
    for (File file : files) {
      result.put(file.path(), file.content());
    }
    return result;
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
