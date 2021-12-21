package silverchain.ag.builder;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apiguardian.api.API;
import silverchain.ag.MethodContext;
import silverchain.ag.ReturnTypeContext;
import silverchain.ag.antlr.AgBaseListener;
import silverchain.ag.antlr.AgParser;

@API(status = API.Status.INTERNAL)
public class AgBuilder extends AgBaseListener {

  @Override
  public void exitReturnType(AgParser.ReturnTypeContext ctx) {
    ReturnTypeContext c = copy(ctx, new ReturnTypeContext(null, 0));
    c.name(c.typeRef().name().getText());
    ctx.getParent().children.replaceAll(t -> t == ctx ? c : t);
  }

  @Override
  public void exitMethod(AgParser.MethodContext ctx) {
    MethodContext c = copy(ctx, new MethodContext(null, 0));
    ctx.getParent().children.replaceAll(t -> c);
  }

  protected <T extends ParserRuleContext> T copy(ParserRuleContext from, T to) {
    to.parent = from.parent;
    to.invokingState = from.invokingState;
    to.start = from.start;
    to.stop = from.stop;
    to.children = from.children;
    to.children.forEach(t -> t.setParent(to));
    return to;
  }
}
