package silverchain.internal.front.validator;

import silverchain.SilverchainException;
import silverchain.internal.front.parser.antlr.AgParser.RepeatSugarContext;

public class ZeroRepetition extends SilverchainException {

  protected static final String FORMAT = "Max number is zero (L%dC%d)";

  public ZeroRepetition(RepeatSugarContext ctx) {
    super(FORMAT, ctx.start.getLine(), ctx.start.getCharPositionInLine());
  }
}
