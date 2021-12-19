package silverchain.internal.frontend.rewriter;

import static silverchain.internal.frontend.rewriter.utility.ContextBuilder.elem;
import static silverchain.internal.frontend.rewriter.utility.ContextBuilder.fact;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.ChainExprContext;
import silverchain.internal.frontend.antlr.AgParser.ChainTermContext;
import silverchain.internal.frontend.antlr.AgParser.PermutationContext;
import silverchain.internal.frontend.core.AgTreeRewriter;

@API(status = API.Status.INTERNAL)
public class PermutationRewriter extends AgTreeRewriter {

  @Override
  public ParseTree visitPermutation(PermutationContext ctx) {
    ChainExprContext c = new ChainExprContext(null, 0);
    c.children = new ArrayList<>();

    ctx = (PermutationContext) super.visitPermutation(ctx);
    List<List<ChainExprContext>> perms = permutation(new ArrayList<>(ctx.chainExpr()));
    terms(perms).forEach(c.children::add);
    return c;
  }

  protected Stream<ChainTermContext> terms(List<List<ChainExprContext>> lists) {
    return lists.stream().map(this::term);
  }

  protected ChainTermContext term(List<ChainExprContext> list) {
    ChainTermContext c = new ChainTermContext(null, 0);
    for (ChainExprContext e : list) c.addAnyChild(fact(elem(e)));
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
