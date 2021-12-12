package silverchain.internal.frontend.checker;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.internal.frontend.parser.antlr.AgParser.RepeatSugarContext;

@API(status = API.Status.INTERNAL)
public class InvalidRange extends SilverchainException {

  protected static final String FORMAT = "min > max (L%dC%d)";

  public InvalidRange(RepeatSugarContext ctx) {
    super(FORMAT, ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
  }
}
