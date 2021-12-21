package silverchain.ag.checker;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.ag.antlr.AgParser.RepeatNMContext;

@API(status = API.Status.INTERNAL)
public class InvalidRepeat extends SilverchainException {

  protected static final String FORMAT = "min=%s > max=%s (L%dC%d)";

  public InvalidRepeat(RepeatNMContext ctx) {
    super(
        FORMAT,
        ctx.INT(0).getText(),
        ctx.INT(1).getText(),
        ctx.start.getLine(),
        ctx.start.getCharPositionInLine() + 1);
  }
}
