package silverchain.process.ag.builder;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apiguardian.api.API;
import silverchain.data.ag.MethodContext;
import silverchain.data.ag.ReturnTypeContext;
import silverchain.data.ag.visitor.AntlrTreeRewriter;
import silverchain.process.ag.antlr.AgParser;

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
