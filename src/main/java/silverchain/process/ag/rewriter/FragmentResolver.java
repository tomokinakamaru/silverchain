package silverchain.process.ag.rewriter;

import static silverchain.process.ag.rewriter.utility.TargetAppender.append;
import static silverchain.process.ag.rewriter.utility.TreeReplicator.replicate;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.data.ag.visitor.AgTreeRewriter;
import silverchain.process.ag.antlr.AgParser.ChainExprContext;
import silverchain.process.ag.antlr.AgParser.FragmentDeclContext;
import silverchain.process.ag.antlr.AgParser.FragmentRefContext;

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
