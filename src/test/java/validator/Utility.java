package validator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import silverchain.diagram.Diagram;
import silverchain.diagram.Diagrams;
import silverchain.parser.Grammar;
import silverchain.parser.Input;
import silverchain.parser.ParseException;
import silverchain.parser.Parser;
import silverchain.validator.JavaValidator;

final class Utility {

  static void validateJava(InputStream stream) {
    new JavaValidator(compile(stream)).validate();
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
