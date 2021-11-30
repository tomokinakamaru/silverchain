package validator;

import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.RecognitionException;
import silverchain.Validator;
import silverchain.diagram.Diagram;
import silverchain.diagram.Diagrams;
import silverchain.parser.AgParser;
import silverchain.parser.Grammar;
import silverchain.parser.Input;
import silverchain.parser.adapter.Parser;

final class Utility {

  static void validateJava(InputStream stream) {
    new Validator(compile(stream)).validate();
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
      return (Input) new Parser(stream).parse(AgParser::input);
    } catch (RecognitionException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
