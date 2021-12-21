package silverchain.ag.rewriter;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public final class AgAssembler {

  private AgAssembler() {}

  public static void add(ParserRuleContext ctx, ParseTree t) {
    ctx.addAnyChild(t);
    t.setParent(ctx);
  }

  public static void replace(ParserRuleContext ctx, ParseTree from, ParseTree to) {
    ctx.children.replaceAll(t -> t == from ? to : t);
    to.setParent(ctx);
  }
}
