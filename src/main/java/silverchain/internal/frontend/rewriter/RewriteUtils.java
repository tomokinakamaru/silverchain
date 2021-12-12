package silverchain.internal.frontend.rewriter;

import java.lang.reflect.Constructor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public final class RewriteUtils {

  private RewriteUtils() {}

  public static <T extends ParserRuleContext> T newInstance(Class<T> cls) {
    try {
      Constructor<T> f = cls.getDeclaredConstructor(ParserRuleContext.class, Integer.TYPE);
      return f.newInstance(null, -1);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T extends ParserRuleContext> T copy(T ctx) {
    @SuppressWarnings("unchecked")
    T c = (T) newInstance(ctx.getClass());
    c.start = ctx.start;
    c.stop = ctx.stop;
    c.parent = ctx.parent;
    c.children = ctx.children;
    return c;
  }

  public static void pushTokens(ParserRuleContext from, ParserRuleContext to) {
    VirtualToken start = new VirtualToken();
    start.token(from.start);
    start.subToken(to.start);
    to.start = start;

    VirtualToken stop = new VirtualToken();
    stop.token(from.stop);
    stop.subToken(to.stop);
    to.stop = stop;
  }

  public static void replaceChild(ParserRuleContext parent, ParseTree from, ParserRuleContext to) {
    parent.children.replaceAll(t -> t == from ? to : t);
    to.parent = parent;
  }
}
