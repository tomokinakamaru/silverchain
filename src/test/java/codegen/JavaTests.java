package codegen;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import silverchain.codegen.GeneratedFile;
import silverchain.codegen.java.JavaGenerator;
import utility.ResourceAnalyzer;

final class JavaTests {

  private static final ResourceAnalyzer analyzer = new ResourceAnalyzer("codegen", "java");

  @Test
  void test1() {
    test("test1.ag");
  }

  private void test(String fileName) {
    List<GeneratedFile> files = new JavaGenerator(analyzer.analyze(fileName)).generate();
    for (GeneratedFile file : files) {
      String generated = file.content();
      String expected = read(fileName, file.path());
      System.out.println(generated);
      assert generated.equals(expected);
    }
  }

  private String read(String inputFile, Path outputPath) {
    Path path = Paths.get(inputFile.replace(".ag", "")).resolve(outputPath);
    return read(path);
  }

  private String read(Path path) {
    Reader reader = new InputStreamReader(analyzer.read(path.toString()));
    return new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
  }
}
