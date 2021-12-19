package internal.frontend.utility;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import silverchain.internal.frontend.antlr.AgLexer;
import silverchain.internal.frontend.antlr.AgParser.InputContext;
import silverchain.internal.frontend.builder.AgParser;
import silverchain.internal.frontend.builder.AgTreeBuilder;
import silverchain.internal.frontend.builder.AntlrParser;

public class Functions {

  public static AntlrParser parser(String text) {
    AgLexer lexer = new AgLexer(CharStreams.fromString(text));
    CommonTokenStream stream = new CommonTokenStream(lexer);
    return new AntlrParser(stream);
  }

  public static InputContext parse(String text) {
    InputContext ctx = new AgParser().parse(CharStreams.fromString(text));
    return (InputContext) ctx.accept(new AgTreeBuilder());
  }

  public static void walk(ParseTreeListener listener, ParseTree tree) {
    ParseTreeWalker.DEFAULT.walk(listener, tree);
  }
}
