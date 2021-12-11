package silverchain.internal.front.rewriter;

import static silverchain.internal.front.rewriter.RewriteUtils.elem;
import static silverchain.internal.front.rewriter.RewriteUtils.expr;
import static silverchain.internal.front.rewriter.RewriteUtils.fact;
import static silverchain.internal.front.rewriter.RewriteUtils.replaceChild;
import static silverchain.internal.front.rewriter.RewriteUtils.term;

import java.util.List;
import silverchain.internal.front.parser.AgBaseListener;
import silverchain.internal.front.parser.AgParser.ChainElemContext;
import silverchain.internal.front.parser.AgParser.ChainExprContext;
import silverchain.internal.front.parser.AgParser.ChainTermContext;
import silverchain.internal.front.parser.AgParser.PermutationContext;

public class PermutationExpander extends AgBaseListener {

  protected ChainExprContext expr;

  @Override
  public void exitChainElem(ChainElemContext ctx) {
    PermutationContext c = ctx.permutation();
    if (ctx.permutation() != null) replaceChild(ctx, c, expr);
  }

  @Override
  public void exitPermutation(PermutationContext ctx) {
    List<ChainExprContext> es = ctx.chainExpr();
    if (es.size() == 1) {
      expr = es.get(0);
    } else {
      expr = permutation(es.get(0), es.get(1));
      for (int i = 2; i < es.size(); i++) {
        expr = permutation(expr, es.get(i));
      }
    }
  }

  protected static ChainExprContext permutation(ChainExprContext expr1, ChainExprContext expr2) {
    ChainTermContext t1 = term(fact(elem(expr1)), term(fact(elem(expr2))));
    ChainTermContext t2 = term(fact(elem(expr2)), term(fact(elem(expr1))));
    return expr(t1, expr(t2));
  }
}
