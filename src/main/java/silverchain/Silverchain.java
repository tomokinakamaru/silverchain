package silverchain;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import silverchain.analyzer.Analyzer;
import silverchain.generator.GeneratedFile;
import silverchain.generator.java.JavaGenerator;
import silverchain.grammar.Grammar;
import silverchain.graph.GraphNode;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

public final class Silverchain {

  private final List<String> args;

  private Path outputDirectory = Paths.get("");

  public Silverchain(String... args) {
    this.args = Arrays.asList(args);
  }

  public static void main(String[] args) throws ParseException, IOException {
    new Silverchain(args).run(System.in);
  }

  public void run(InputStream stream) throws ParseException, IOException {
    for (int i = 0; i < args.size(); i++) {
      String arg = args.get(i);
      if (arg.equals("-o")) {
        outputDirectory = Paths.get(args.get(i + 1));
        i++;
      } else {
        throw new RuntimeException("Unknown argument: " + arg);
      }
    }

    Parser parser = new Parser(stream);
    Grammar grammar = parser.grammar();
    Analyzer analyzer = new Analyzer(grammar);
    List<GraphNode> nodes = analyzer.analyze();
    JavaGenerator generator = new JavaGenerator(nodes);
    for (GeneratedFile file : generator.generate()) {
      file.save(outputDirectory);
    }
  }
}
