package silverchain.internal.frontend.rewriter;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.ChainElemContext;
import silverchain.internal.frontend.antlr.AgParser.ChainExprContext;
import silverchain.internal.frontend.antlr.AgParser.FragmentDeclContext;
import silverchain.internal.frontend.antlr.AgParser.FragmentRefContext;
import silverchain.internal.frontend.utility.ContextReplicator;
import silverchain.internal.frontend.utility.ContextRewriter;

@API(status = API.Status.INTERNAL)
public class FragmentResolver extends ContextRewriter {

  protected final Map<String, ChainExprContext> fragments = new HashMap<>();

  @Override
  public ParseTree visitFragmentDecl(FragmentDeclContext ctx) {
    ctx = (FragmentDeclContext) super.visitFragmentDecl(ctx);
    fragments.put(ctx.FRAGMENT_ID().getText(), ctx.chainExpr());
    return null;
  }

  @Override
  public ParseTree visitChainElem(ChainElemContext ctx) {
    ctx = (ChainElemContext) ctx.accept(new ContextReplicator());
    FragmentRefContext r = ctx.fragmentRef();
    if (r != null) {
      ChainExprContext refCtx = fragments.get(r.FRAGMENT_ID().getText());
      ChainExprContext newCtx = (ChainExprContext) refCtx.accept(new ContextReplicator());
      ChainElemContext elem = (ChainElemContext) ctx.accept(new ContextReplicator());
      elem.children.replaceAll(t -> t == elem.fragmentRef() ? newCtx : t);
      return elem;
    }
    return ctx;
  }
}
