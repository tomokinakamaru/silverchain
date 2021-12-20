package silverchain.process.ag.rewriter.utility;

import org.apiguardian.api.API;
import silverchain.process.ag.antlr.AgParser.ChainElemContext;
import silverchain.process.ag.antlr.AgParser.ChainExprContext;
import silverchain.process.ag.antlr.AgParser.ChainFactContext;

@API(status = API.Status.INTERNAL)
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
