package internal.utility;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.params.provider.Arguments;
import silverchain.internal.frontend.parser.AgParser;
import silverchain.internal.frontend.parser.antlr.AgLexer;
import silverchain.internal.frontend.parser.antlr.AgParser.InputContext;

public class Functions {

  public static Arguments args(Selector selector, String text, String expected) {
    return Arguments.of(selector, text, expected);
  }

  public static InputContext parse(String text) {
    return new AgParser().parse(CharStreams.fromString(text));
  }

  public static silverchain.internal.frontend.parser.antlr.AgParser parser(String text) {
    AgLexer lexer = new AgLexer(CharStreams.fromString(text));
    return new silverchain.internal.frontend.parser.antlr.AgParser(new CommonTokenStream(lexer));
  }

  public static void walk(ParseTreeListener listener, ParseTree tree) {
    ParseTreeWalker.DEFAULT.walk(listener, tree);
  }
}
