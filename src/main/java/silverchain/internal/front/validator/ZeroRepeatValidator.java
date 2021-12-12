package silverchain.internal.front.validator;

import static java.lang.Integer.parseInt;

import silverchain.internal.front.parser.antlr.AgBaseListener;
import silverchain.internal.front.parser.antlr.AgParser.RepeatSugarContext;

public class ZeroRepeatValidator extends AgBaseListener {

  @Override
  public void enterRepeatSugar(RepeatSugarContext ctx) {
    if (ctx.COMMA != null && ctx.MAX != null && parseInt(ctx.MAX.getText()) == 0) {
      throw new ZeroRepeat(ctx);
    }
  }
}
