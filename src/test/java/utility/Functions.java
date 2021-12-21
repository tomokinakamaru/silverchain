package utility;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import silverchain.ag.antlr.AgParser.InputContext;
import silverchain.ag.builder.AgBuilder;
import silverchain.ag.builder.AgLexer;
import silverchain.ag.builder.AgParser;

public class Functions {

  public static AgParser parser(String text) {
    silverchain.ag.antlr.AgLexer lexer =
        new silverchain.ag.antlr.AgLexer(CharStreams.fromString(text));
    CommonTokenStream stream = new CommonTokenStream(lexer);
    return new AgParser(stream);
  }

  public static InputContext parse(String text) {
    AgLexer lexer = new AgLexer(CharStreams.fromString(text));
    AgParser parser = new AgParser(new CommonTokenStream(lexer));
    InputContext ctx = parser.input();
    ParseTreeWalker.DEFAULT.walk(new AgBuilder(), ctx);
    return ctx;
  }

  public static void walk(ParseTreeListener listener, ParseTree tree) {
    ParseTreeWalker.DEFAULT.walk(listener, tree);
  }
}
