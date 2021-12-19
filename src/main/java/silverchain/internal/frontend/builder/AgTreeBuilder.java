package silverchain.internal.frontend.builder;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser;
import silverchain.internal.frontend.core.AntlrTreeRewriter;
import silverchain.internal.frontend.core.data.MethodContext;
import silverchain.internal.frontend.core.data.ReturnTypeContext;

@API(status = API.Status.INTERNAL)
public class AgTreeBuilder extends AntlrTreeRewriter {

  @Override
  public ParserRuleContext visitReturnType(AgParser.ReturnTypeContext ctx) {
    ParserRuleContext c = (ParserRuleContext) super.visitReturnType(ctx);
    return build(ReturnTypeContext::new, c, ctx.children);
  }

  @Override
  public ParserRuleContext visitMethod(AgParser.MethodContext ctx) {
    ParserRuleContext c = (ParserRuleContext) super.visitMethod(ctx);
    return build(MethodContext::new, c, ctx.children);
  }
}
