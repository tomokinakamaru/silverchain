package silverchain.internal.front.validator;

import silverchain.SilverchainException;
import silverchain.internal.front.parser.antlr.AgParser.RepeatSugarContext;

public class InvalidRepetition extends SilverchainException {

  protected static final String FORMAT = "min > max (L%dC%d)";

  public InvalidRepetition(RepeatSugarContext ctx) {
    super(FORMAT, ctx.start.getLine(), ctx.start.getCharPositionInLine());
  }
}
