package silverchain.internal.frontend.rewriter;

import static silverchain.internal.frontend.rewriter.RewriteUtils.copy;
import static silverchain.internal.frontend.rewriter.RewriteUtils.pushTokens;
import static silverchain.internal.frontend.rewriter.RewriteUtils.replaceChild;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.internal.frontend.parser.antlr.AgBaseListener;
import silverchain.internal.frontend.parser.antlr.AgParser.ChainElemContext;
import silverchain.internal.frontend.parser.antlr.AgParser.ChainExprContext;
import silverchain.internal.frontend.parser.antlr.AgParser.FragmentDeclContext;
import silverchain.internal.frontend.parser.antlr.AgParser.FragmentRefContext;

@API(status = API.Status.INTERNAL)
public class FragmentResolver extends AgBaseListener {

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
      ChainExprContext newCtx = copy(refCtx);
      pushTokens(oldCtx, newCtx);
      replaceChild(ctx, oldCtx, newCtx);
    }
  }
}
