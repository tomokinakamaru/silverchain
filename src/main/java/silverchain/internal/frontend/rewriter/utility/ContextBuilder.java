package silverchain.internal.frontend.rewriter.utility;

import silverchain.internal.frontend.antlr.AgParser.ChainElemContext;
import silverchain.internal.frontend.antlr.AgParser.ChainExprContext;
import silverchain.internal.frontend.antlr.AgParser.ChainFactContext;

public final class ContextBuilder {

  private ContextBuilder() {}

  public static ChainFactContext fact(ChainElemContext ctx) {
    ChainFactContext c = new ChainFactContext(null, 0);
    c.addAnyChild(ctx);
    return c;
  }

  public static ChainElemContext elem(ChainExprContext ctx) {
    ChainElemContext c = new ChainElemContext(null, 0);
    c.addAnyChild(ctx);
    return c;
  }
}
