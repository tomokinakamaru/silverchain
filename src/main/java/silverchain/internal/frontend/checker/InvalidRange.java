package silverchain.internal.frontend.checker;

import silverchain.SilverchainException;
import silverchain.internal.frontend.parser.antlr.AgParser.RepeatSugarContext;

public class InvalidRange extends SilverchainException {

  protected static final String FORMAT = "min > max (L%dC%d)";

  public InvalidRange(RepeatSugarContext ctx) {
    super(FORMAT, ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
  }
}
