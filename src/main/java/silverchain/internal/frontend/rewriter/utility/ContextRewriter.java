package silverchain.internal.frontend.rewriter.utility;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public abstract class ContextRewriter extends ContextBuilder {

  @Override
  protected ParseTree build(ParserRuleContext ctx, DefaultConstructor constructor) {
    boolean changed = false;
    List<ParseTree> trees = new ArrayList<>();
    for (ParseTree tree : ctx.children) {
      ParseTree t = tree.accept(this);
      changed |= tree != t;
      if (t != null) trees.add(t);
    }
    if (changed) {
      ParserRuleContext c = constructor.apply(null, 0);
      c.copyFrom(ctx);
      c.children = trees;
      return c;
    }
    return ctx;
  }
}
