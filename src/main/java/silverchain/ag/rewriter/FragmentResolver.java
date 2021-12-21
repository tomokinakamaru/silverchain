package silverchain.ag.rewriter;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.Token;
import org.apiguardian.api.API;
import silverchain.ag.AgListener;
import silverchain.ag.MethodContext;
import silverchain.ag.antlr.AgParser.ChainExprContext;
import silverchain.ag.antlr.AgParser.FragmentDeclContext;
import silverchain.ag.antlr.AgParser.FragmentRefContext;

@API(status = API.Status.INTERNAL)
public class FragmentResolver extends AgListener {

  protected Map<String, ChainExprContext> fragments = new HashMap<>();

  protected Token target;

  @Override
  public void exitFragmentDecl(FragmentDeclContext ctx) {
    fragments.put(ctx.FRAGMENT_ID().getText(), ctx.chainExpr());
  }

  @Override
  public void exitFragmentRef(FragmentRefContext ctx) {
    ChainExprContext c = AgReplicator.replicate(fragments.get(ctx.FRAGMENT_ID().getText()));
    AgFinder.find(MethodContext.class, c).forEach(m -> m.targets().add(ctx.start));
    AgAssembler.replace(ctx.getParent(), ctx, c);
  }
}
