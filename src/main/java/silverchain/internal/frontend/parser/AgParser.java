package silverchain.internal.frontend.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.apiguardian.api.API;
import silverchain.internal.front.parser.antlr.AgLexer;
import silverchain.internal.front.parser.antlr.AgParser.InputContext;

@API(status = API.Status.INTERNAL)
public class AgParser {

  public InputContext parse(CharStream stream) {
    return parser(lexer(stream)).input();
  }

  protected AgLexer lexer(CharStream stream) {
    AgLexer lexer = new AgLexer(stream);
    lexer.removeErrorListeners();
    lexer.addErrorListener(new SyntaxErrorListener());
    return lexer;
  }

  protected silverchain.internal.front.parser.antlr.AgParser parser(AgLexer lexer) {
    TokenStream stream = new CommonTokenStream(lexer);
    AntlrParser parser = new AntlrParser(stream);
    parser.removeErrorListeners();
    parser.addErrorListener(new SyntaxErrorListener());
    return parser;
  }

  protected static class AntlrParser extends silverchain.internal.front.parser.antlr.AgParser {
    protected AntlrParser(TokenStream input) {
      super(input);
    }
  }
}
