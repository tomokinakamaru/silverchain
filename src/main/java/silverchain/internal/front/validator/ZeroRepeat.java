package silverchain.internal.front.validator;

import silverchain.SilverchainException;
import silverchain.internal.front.parser.antlr.AgParser.RepeatSugarContext;

public class ZeroRepeat extends SilverchainException {

  protected static final String FORMAT = "Max is zero (L%dC%d)";

  public ZeroRepeat(RepeatSugarContext ctx) {
    super(FORMAT, ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
  }
}
