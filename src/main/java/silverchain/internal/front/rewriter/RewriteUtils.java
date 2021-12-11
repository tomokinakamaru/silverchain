package silverchain.internal.front.rewriter;

import java.lang.reflect.Constructor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import silverchain.internal.front.parser.antlr.AgParser.ChainElemContext;
import silverchain.internal.front.parser.antlr.AgParser.ChainExprContext;
import silverchain.internal.front.parser.antlr.AgParser.ChainFactContext;
import silverchain.internal.front.parser.antlr.AgParser.ChainTermContext;
import silverchain.internal.front.parser.antlr.AgParser.RepeatContext;

public final class RewriteUtils {

  private RewriteUtils() {}

  public static ChainExprContext expr(ChainTermContext term) {
    ChainExprContext c = newInstance(ChainExprContext.class);
    addChild(c, term);
    return c;
  }

  public static ChainExprContext expr(ChainTermContext term, ChainExprContext expr) {
    ChainExprContext c = expr(term);
    addChild(c, expr);
    return c;
  }

  public static ChainTermContext term(ChainFactContext fact) {
    ChainTermContext c = newInstance(ChainTermContext.class);
    addChild(c, fact);
    return c;
  }

  public static ChainTermContext term(ChainFactContext fact, ChainTermContext term) {
    ChainTermContext c = term(fact);
    addChild(c, term);
    return c;
  }

  public static ChainFactContext fact(ChainElemContext elem) {
    ChainFactContext c = newInstance(ChainFactContext.class);
    addChild(c, elem);
    return c;
  }

  public static ChainFactContext fact(ChainElemContext elem, RepeatContext repeat) {
    ChainFactContext c = fact(elem);
    addChild(c, repeat);
    return c;
  }

  public static ChainElemContext elem(ChainExprContext expr) {
    ChainElemContext c = newInstance(ChainElemContext.class);
    addChild(c, expr);
    return c;
  }

  public static <T extends ParserRuleContext> T newInstance(Class<T> cls) {
    try {
      Constructor<T> f = cls.getDeclaredConstructor(ParserRuleContext.class, Integer.class);
      return f.newInstance(null, -1);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T extends ParserRuleContext> T copy(T ctx) {
    @SuppressWarnings("unchecked")
    T c = (T) copy(ctx, ctx.getClass());
    return c;
  }

  public static <T extends ParserRuleContext> T copy(ParserRuleContext ctx, Class<T> cls) {
    T c = newInstance(cls);
    copyInterval(ctx, c);
    return c;
  }

  public static <T extends ParserRuleContext> T deepCopy(T ctx) {
    T c = copy(ctx);
    c.parent = ctx.parent;
    c.children = ctx.children;
    return c;
  }

  public static void copyInterval(ParserRuleContext from, ParserRuleContext to) {
    to.start = from.start;
    to.stop = from.stop;
  }

  public static void pushInterval(ParserRuleContext from, ParserRuleContext to) {
    VirtualToken start = new VirtualToken();
    start.token(from.start);
    start.subToken(to.start);
    VirtualToken stop = new VirtualToken();
    stop.token(from.stop);
    stop.subToken(to.stop);
    to.start = start;
    to.stop = stop;
  }

  public static void addChild(ParserRuleContext parent, ParserRuleContext child) {
    parent.addAnyChild(child);
    parent.start = parent.start == null ? child.start : parent.start;
    parent.stop = child.stop;
    child.parent = parent;
  }

  public static void replaceChild(ParserRuleContext parent, ParseTree from, ParserRuleContext to) {
    parent.children.replaceAll(t -> t == from ? to : t);
    to.parent = parent;
  }

  public static void removeChild(ParserRuleContext parent, ParserRuleContext child) {
    if (parent.children != null) parent.children.remove(child);
  }
}
