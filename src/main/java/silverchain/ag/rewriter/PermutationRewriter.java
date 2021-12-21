package silverchain.ag.rewriter;

import java.util.ArrayList;
import java.util.List;
import org.apiguardian.api.API;
import silverchain.ag.AgListener;
import silverchain.ag.antlr.AgParser.ChainExprContext;
import silverchain.ag.antlr.AgParser.ChainTermContext;
import silverchain.ag.antlr.AgParser.PermutationContext;

@API(status = API.Status.INTERNAL)
public class PermutationRewriter extends AgListener {

  @Override
  public void exitPermutation(PermutationContext ctx) {
    ChainExprContext c = AgFactory.context(ChainExprContext.class, ctx);
    List<List<ChainExprContext>> perms = permutation(new ArrayList<>(ctx.chainExpr()));
    perms.stream().map(this::term).forEach(t -> AgAssembler.add(c, t));
    AgAssembler.replace(ctx.getParent(), ctx, c);
  }

  protected ChainTermContext term(List<ChainExprContext> list) {
    ChainTermContext c = AgFactory.context(ChainTermContext.class);
    for (ChainExprContext e : list) AgAssembler.add(c, AgFactory.fact(AgFactory.elem(e)));
    return c;
  }

  protected List<List<ChainExprContext>> permutation(List<ChainExprContext> list) {
    if (list.isEmpty()) {
      List<List<ChainExprContext>> result = new ArrayList<>();
      result.add(new ArrayList<>());
      return result;
    }
    ChainExprContext expr = list.remove(0);
    List<List<ChainExprContext>> result = new ArrayList<>();
    List<List<ChainExprContext>> perms = permutation(list);
    for (List<ChainExprContext> p : perms) {
      for (int i = 0; i <= p.size(); i++) {
        List<ChainExprContext> temp = new ArrayList<>(p);
        temp.add(i, expr);
        result.add(temp);
      }
    }
    return result;
  }
}
