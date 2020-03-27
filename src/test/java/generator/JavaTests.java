package generator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import silverchain.analyzer.Analyzer;
import silverchain.generator.GeneratedFile;
import silverchain.generator.java.JavaGenerator;
import silverchain.grammar.Grammars;
import silverchain.graph.GraphNode;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

public final class JavaTests {

  private static final Path resources = Paths.get("src").resolve("test").resolve("resources");

  @Test
  void test1() throws FileNotFoundException, ParseException {
    test("test1");
  }

  @Test
  void test2() throws FileNotFoundException, ParseException {
    test("test2");
  }

  @Test
  void test3() throws FileNotFoundException, ParseException {
    test("test3");
  }

  private void test(String name) throws ParseException, FileNotFoundException {
    Path path = resources.resolve("java").resolve(name + ".ag");
    InputStream stream = new FileInputStream(path.toString());
    Grammars grammars = new Parser(stream).grammars();
    Analyzer analyzer = new Analyzer(grammars);

    List<GeneratedFile> files = new ArrayList<>();
    for (List<GraphNode> nodes : analyzer.analyze()) {
      JavaGenerator generator = new JavaGenerator(nodes);
      files.addAll(generator.generate());
    }
    GeneratedFileTester tester = new GeneratedFileTester("java", name);
    tester.test(files);
  }
}
