package silverchain.data.ag.visitor;

import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.data.ag.MethodContext;
import silverchain.data.ag.ReturnTypeContext;
import silverchain.process.ag.antlr.AgParser;

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
