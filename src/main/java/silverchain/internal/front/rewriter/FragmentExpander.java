package silverchain.internal.front.rewriter;

import static silverchain.internal.front.rewriter.RewriteUtils.deepCopy;
import static silverchain.internal.front.rewriter.RewriteUtils.pushInterval;
import static silverchain.internal.front.rewriter.RewriteUtils.replaceChild;

import java.util.HashMap;
import java.util.Map;
import silverchain.internal.front.parser.AgBaseListener;
import silverchain.internal.front.parser.AgParser.ChainElemContext;
import silverchain.internal.front.parser.AgParser.ChainExprContext;
import silverchain.internal.front.parser.AgParser.FragmentDeclContext;
import silverchain.internal.front.parser.AgParser.FragmentRefContext;

public class FragmentExpander extends AgBaseListener {

  protected final Map<String, ChainExprContext> fragments = new HashMap<>();

  @Override
  public void exitFragmentDecl(FragmentDeclContext ctx) {
    fragments.put(ctx.FRAGMENT_ID().getText(), ctx.chainExpr());
  }

  @Override
  public void exitChainElem(ChainElemContext ctx) {
    FragmentRefContext oldCtx = ctx.fragmentRef();
    if (oldCtx != null) {
      ChainExprContext refCtx = fragments.get(oldCtx.FRAGMENT_ID().getText());
      ChainExprContext newCtx = deepCopy(refCtx);
      pushInterval(oldCtx, newCtx);
      replaceChild(ctx, oldCtx, newCtx);
    }
  }
}
