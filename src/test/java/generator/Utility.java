package generator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import silverchain.diagram.Diagram;
import silverchain.generator.GeneratedFile;
import silverchain.generator.JavaGenerator;
import silverchain.parser.Grammar;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

final class Utility {

  static List<GeneratedFile> generateJava(InputStream stream) {
    return new JavaGenerator(compile(stream)).generate();
  }

  static InputStream read(Path path) {
    try {
      return new FileInputStream(path.toString());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private static List<Diagram> compile(InputStream stream) {
    List<Diagram> diagrams = new ArrayList<>();
    for (Grammar grammar : parse(stream)) {
      Diagram diagram = grammar.diagram();
      diagram.compile();
      diagrams.add(diagram);
    }
    return diagrams;
  }

  private static List<Grammar> parse(InputStream stream) {
    try {
      return new Parser(stream).start();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
