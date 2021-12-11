package silverchain.internal.front.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import silverchain.internal.front.parser.AgParser.InputContext;

public class Parser {

  public InputContext parse(CharStream stream) {
    return parser(lexer(stream)).input();
  }

  protected AgLexer lexer(CharStream stream) {
    AgLexer lexer = new AgLexer(stream);
    lexer.removeErrorListeners();
    lexer.addErrorListener(new SyntaxErrorListener());
    return lexer;
  }

  protected AgParser parser(AgLexer lexer) {
    TokenStream stream = new CommonTokenStream(lexer);
    AgParser parser = new AgParser(stream);
    parser.removeErrorListeners();
    parser.addErrorListener(new SyntaxErrorListener());
    return parser;
  }
}
