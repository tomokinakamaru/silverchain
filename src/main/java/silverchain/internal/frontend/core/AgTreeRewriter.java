package silverchain.internal.frontend.core;

import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser;
import silverchain.internal.frontend.core.data.MethodContext;
import silverchain.internal.frontend.core.data.ReturnTypeContext;

@API(status = API.Status.INTERNAL)
public abstract class AgTreeRewriter extends AntlrTreeRewriter {

  public ParseTree visitReturnType(ReturnTypeContext ctx) {
    ReturnTypeContext c = (ReturnTypeContext) build(ReturnTypeContext::new, ctx);
    if (c != ctx) c.sources(ctx.sources());
    return c;
  }

  public ParseTree visitMethod(MethodContext ctx) {
    MethodContext c = (MethodContext) build(MethodContext::new, ctx);
    if (c != ctx) c.targets(ctx.targets());
    return c;
  }

  @Override
  public ParseTree visitReturnType(AgParser.ReturnTypeContext ctx) {
    return visitReturnType((ReturnTypeContext) ctx);
  }

  @Override
  public ParseTree visitMethod(AgParser.MethodContext ctx) {
    return visitMethod((MethodContext) ctx);
  }
}
