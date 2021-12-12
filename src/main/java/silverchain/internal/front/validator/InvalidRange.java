package silverchain.internal.front.validator;

import silverchain.SilverchainException;
import silverchain.internal.front.parser.antlr.AgParser.RepeatSugarContext;

public class InvalidRange extends SilverchainException {

  protected static final String FORMAT = "min > max (L%dC%d)";

  public InvalidRange(RepeatSugarContext ctx) {
    super(FORMAT, ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
  }
}
