package silverchain.internal.front.rewriter;

import static silverchain.internal.front.rewriter.RewriteUtils.addChild;
import static silverchain.internal.front.rewriter.RewriteUtils.copy;
import static silverchain.internal.front.rewriter.RewriteUtils.elem;
import static silverchain.internal.front.rewriter.RewriteUtils.expr;
import static silverchain.internal.front.rewriter.RewriteUtils.fact;
import static silverchain.internal.front.rewriter.RewriteUtils.removeChild;
import static silverchain.internal.front.rewriter.RewriteUtils.replaceChild;
import static silverchain.internal.front.rewriter.RewriteUtils.term;

import java.util.function.Supplier;
import silverchain.internal.front.parser.antlr.AgBaseListener;
import silverchain.internal.front.parser.antlr.AgParser.ChainElemContext;
import silverchain.internal.front.parser.antlr.AgParser.ChainFactContext;
import silverchain.internal.front.parser.antlr.AgParser.ChainTermContext;
import silverchain.internal.front.parser.antlr.AgParser.RepeatContext;
import silverchain.internal.front.parser.antlr.AgParser.RepeatSugarContext;

public class RepeatSugarExpander extends AgBaseListener {

  protected ChainFactContext fact;

  protected ChainElemContext elem;

  protected RepeatSugarContext sugar;

  protected int min;

  protected Integer max;

  @Override
  public void exitChainFact(ChainFactContext ctx) {
    sugar = ctx.repeatSugar();
    if (sugar != null) {
      elem = ctx.chainElem();
      removeChild(ctx, sugar);
      if (min == 0) {
        if (max == null) {
          rewrite0X();
        } else if (max == 1) {
          rewrite01();
        } else {
          rewrite0N();
        }
      } else {
        if (max == null) {
          rewriteNX();
        } else if (min == max) {
          rewriteNN();
        } else {
          rewriteNM();
        }
      }
    }
  }

  @Override
  public void exitRepeatSugar(RepeatSugarContext ctx) {
    if (ctx.ONE_MORE == null) {
      min = Integer.parseInt(ctx.MIN.getText());
      if (ctx.COMMA == null) {
        max = min;
      } else if (ctx.MAX == null) {
        max = null;
      } else {
        max = Integer.parseInt(ctx.MAX.getText());
      }
    } else {
      min = 0;
      max = 1;
    }
  }

  protected void rewrite0X() {
    // elem[0,] ~> elem*
    addChild(fact, zeroMore());
  }

  protected void rewrite01() {
    // elem[0,1] ~> elem?
    addChild(fact, zeroOne());
  }

  protected void rewrite0N() {
    // elem[0,max] ~> elem? × max
    ChainTermContext term = repeat(() -> fact(copy(elem), zeroOne()), max);
    replaceChild(fact, elem, elem(expr(term)));
  }

  protected void rewriteNX() {
    // elem[min,] (0 < min) ~> elem × min + elem*
    ChainFactContext head = fact(copy(elem), zeroMore());
    ChainTermContext tail = repeat(() -> fact(copy(elem)), min);
    replaceChild(fact, elem, elem(expr(term(head, tail))));
  }

  protected void rewriteNN() {
    // elem[min] ~> e × min
    ChainTermContext term = repeat(() -> fact(copy(elem)), min);
    replaceChild(fact, elem, elem(expr(term)));
  }

  protected void rewriteNM() {
    // elem[min,max] (0 < min && min < max) ~> e × min + e? × (max - min)
    ChainTermContext head = repeat(() -> fact(copy(elem)), min);
    ChainTermContext tail = repeat(() -> fact(copy(elem), zeroOne()), max - min);
    replaceChild(fact, elem, elem(expr(term(fact(elem(expr(head))), tail))));
  }

  protected RepeatContext zeroOne() {
    RepeatContext r = copy(sugar, RepeatContext.class);
    r.ZERO_ONE = new VirtualToken();
    return r;
  }

  protected RepeatContext zeroMore() {
    RepeatContext r = copy(sugar, RepeatContext.class);
    r.ZERO_MORE = new VirtualToken();
    return r;
  }

  protected static ChainTermContext repeat(Supplier<ChainFactContext> f, int n) {
    ChainTermContext term = term(f.get());
    for (int i = 1; i < n; i++) term = term(f.get(), term);
    return term;
  }
}
