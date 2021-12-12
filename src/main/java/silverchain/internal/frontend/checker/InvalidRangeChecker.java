package silverchain.internal.frontend.checker;

import static java.lang.Integer.parseInt;

import org.antlr.v4.runtime.Token;
import silverchain.internal.front.parser.antlr.AgBaseListener;
import silverchain.internal.front.parser.antlr.AgParser.RepeatSugarContext;

public class InvalidRangeChecker extends AgBaseListener {

  @Override
  public void enterRepeatSugar(RepeatSugarContext ctx) {
    Token min = ctx.MIN;
    Token max = ctx.MAX;
    if (min != null && max != null && parseInt(min.getText()) > parseInt(max.getText())) {
      throw new InvalidRange(ctx);
    }
  }
}
