package silverchain.internal.frontend.builder;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgLexer;
import silverchain.internal.frontend.antlr.AgParser.InputContext;

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

  protected AntlrParser parser(AgLexer lexer) {
    AntlrParser parser = new AntlrParser(new CommonTokenStream(lexer));
    parser.removeErrorListeners();
    parser.addErrorListener(new SyntaxErrorListener());
    return parser;
  }
}
