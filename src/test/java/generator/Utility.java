package generator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import silverchain.command.WarningPrinter;
import silverchain.diagram.Diagram;
import silverchain.diagram.Diagrams;
import silverchain.generator.GeneratedFile;
import silverchain.generator.JavaGenerator;
import silverchain.javadoc.Javadocs;
import silverchain.parser.Grammar;
import silverchain.parser.Input;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;

final class Utility {

  static List<GeneratedFile> generateJava(InputStream stream, String javadocPath) {
    return new JavaGenerator(compile(stream), new Javadocs(javadocPath, new WarningPrinter()))
        .generate();
  }

  static InputStream read(Path path) {
    try {
      return new FileInputStream(path.toString());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private static Diagrams compile(InputStream stream) {
    Diagrams diagrams = new Diagrams();
    Input input = parse(stream);
    for (Grammar grammar : input.grammars()) {
      Diagram diagram = grammar.diagram(input.importMap());
      diagram.compile();
      diagrams.add(diagram);
    }
    return diagrams;
  }

  private static Input parse(InputStream stream) {
    try {
      return new Parser(stream).start();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
