import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.Silverchain;

final class SilverchainTest {

  private static final Path INPUT = Paths.get("sample/src/main/silverchain");

  private static final Path OUTPUT = Paths.get("build/silverchain/test-results");

  private static final Path EXPECTED = Paths.get("sample/src/main/gen");

  private static Arguments[] data() throws IOException {
    return find("ag", INPUT).stream()
        .map(Path::getFileName)
        .map(Path::toString)
        .map(SilverchainTest::args)
        .toArray(Arguments[]::new);
  }

  @ParameterizedTest(name = "[{index}] \"{0}\"")
  @MethodSource("data")
  void test(Path input, Path output, Path expected) throws Exception {
    delete(output);
    Silverchain silverchain = new Silverchain();
    silverchain.setInput(input.toString());
    silverchain.setOutput(output.toString());
    silverchain.call();
    assertThat(load(output)).isEqualTo(load(expected));
  }

  private static List<Path> find(String extension, Path path) throws IOException {
    String ext = "." + extension;
    return Files.walk(path).filter(p -> p.toString().endsWith(ext)).collect(Collectors.toList());
  }

  private static Map<Path, String> load(Path path) throws IOException {
    Map<Path, String> map = new HashMap<>();
    for (Path p : find("java", path)) map.put(path.relativize(p), read(p));
    return map;
  }

  private static String read(Path path) throws IOException {
    return new String(Files.readAllBytes(path));
  }

  private static void delete(Path path) throws IOException {
    File file = path.toFile();
    if (file.exists()) FileUtils.forceDelete(file);
  }

  private static Arguments args(String name) {
    return Arguments.of(INPUT.resolve(name), OUTPUT.resolve(name), EXPECTED.resolve(name));
  }
}
