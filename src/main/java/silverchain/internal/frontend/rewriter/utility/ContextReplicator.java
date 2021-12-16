package silverchain.internal.frontend.rewriter.utility;

import java.util.ArrayList;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class ContextReplicator extends ContextBuilder {

  @Override
  protected ParseTree build(ParserRuleContext ctx, DefaultConstructor constructor) {
    ParserRuleContext c = constructor.apply(null, 0);
    c.copyFrom(ctx);
    c.children = new ArrayList<>();
    for (ParseTree t : ctx.children) {
      c.children.add(t.accept(this));
    }
    return c;
  }
}
