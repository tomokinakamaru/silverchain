package silverchain.parser.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import silverchain.parser.ASTNode;
import silverchain.parser.AgLexer;
import silverchain.parser.AgParser;

public final class Parser {

  private final InputStream stream;

  public Parser(InputStream stream) {
    this.stream = stream;
  }

  public ASTNode parse(Function<AgParser, ParseTree> parse) throws IOException {
    AgLexer lexer = new AgLexer(CharStreams.fromStream(stream));
    lexer.removeErrorListeners();
    lexer.addErrorListener(TokenizeErrorListener.TOKENIZE_ERROR_LISTENER);
    AgParser parser = new AgParser(new CommonTokenStream(lexer));
    parser.removeErrorListeners();
    parser.addErrorListener(ParseErrorListener.PARSE_ERROR_LISTENER);
    ParseTree tree = parse.apply(parser);
    return new ASTBuilder().visit(tree);
  }
}
