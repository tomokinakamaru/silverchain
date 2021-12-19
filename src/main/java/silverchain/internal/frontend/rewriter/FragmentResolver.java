package silverchain.internal.frontend.rewriter;

import static silverchain.internal.frontend.rewriter.utility.TargetAppender.append;
import static silverchain.internal.frontend.rewriter.utility.TreeReplicator.replicate;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.ChainExprContext;
import silverchain.internal.frontend.antlr.AgParser.FragmentDeclContext;
import silverchain.internal.frontend.antlr.AgParser.FragmentRefContext;
import silverchain.internal.frontend.core.AgTreeRewriter;

@API(status = API.Status.INTERNAL)
public class FragmentResolver extends AgTreeRewriter {

  protected Map<String, ChainExprContext> fragments = new HashMap<>();

  @Override
  public ParseTree visitFragmentDecl(FragmentDeclContext ctx) {
    ctx = (FragmentDeclContext) super.visitFragmentDecl(ctx);
    fragments.put(ctx.FRAGMENT_ID().getText(), ctx.chainExpr());
    return null;
  }

  @Override
  public ParseTree visitFragmentRef(FragmentRefContext ctx) {
    ChainExprContext c = replicate(fragments.get(ctx.FRAGMENT_ID().getText()));
    return append(c, ctx.start);
  }
}
