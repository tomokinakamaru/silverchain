package silverchain.internal.frontend.checker;

import static java.lang.Integer.parseInt;

import silverchain.internal.front.parser.antlr.AgBaseListener;
import silverchain.internal.front.parser.antlr.AgParser.RepeatSugarContext;

public class ZeroRepeatChecker extends AgBaseListener {

  @Override
  public void enterRepeatSugar(RepeatSugarContext ctx) {
    if (ctx.COMMA != null && ctx.MAX != null && parseInt(ctx.MAX.getText()) == 0) {
      throw new ZeroRepeat(ctx);
    }
  }
}
