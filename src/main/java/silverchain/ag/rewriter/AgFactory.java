package silverchain.ag.rewriter;

import java.util.ArrayList;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.apiguardian.api.API;
import silverchain.ag.antlr.AgLexer;
import silverchain.ag.antlr.AgParser.ChainElemContext;
import silverchain.ag.antlr.AgParser.ChainExprContext;
import silverchain.ag.antlr.AgParser.ChainFactContext;

@API(status = API.Status.INTERNAL)
public final class AgFactory {

  private AgFactory() {}

  public static ChainFactContext fact(ChainElemContext ctx) {
    ChainFactContext c = context(ChainFactContext.class);
    AgAssembler.add(c, ctx);
    return c;
  }

  public static ChainFactContext fact(ChainElemContext ctx, TerminalNode node) {
    ChainFactContext c = fact(ctx);
    AgAssembler.add(c, node);
    return c;
  }

  public static ChainElemContext elem(ChainExprContext ctx) {
    ChainElemContext c = context(ChainElemContext.class);
    AgAssembler.add(c, ctx);
    return c;
  }

  public static TerminalNode terminal(int i) {
    String s = AgLexer.VOCABULARY.getLiteralName(i).replace("'", "");
    return new TerminalNodeImpl(new CommonToken(i, s));
  }

  public static TerminalNode terminal(TerminalNode node) {
    return new TerminalNodeImpl(node.getSymbol());
  }

  public static <T extends ParserRuleContext> T context(Class<T> cls, ParserRuleContext ctx) {
    T c = context(cls);
    c.copyFrom(ctx);
    c.children = new ArrayList<>();
    return c;
  }

  public static <T extends ParserRuleContext> T context(Class<T> cls) {
    try {
      return cls.getDeclaredConstructor(ParserRuleContext.class, Integer.TYPE).newInstance(null, 0);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }
}
