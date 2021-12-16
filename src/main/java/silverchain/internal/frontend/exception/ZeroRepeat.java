package silverchain.internal.frontend.exception;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.internal.frontend.antlr.AgParser.RepeatContext;

@API(status = API.Status.INTERNAL)
public class ZeroRepeat extends SilverchainException {

  protected static final String FORMAT = "Max is zero (L%dC%d)";

  public ZeroRepeat(RepeatContext ctx) {
    super(FORMAT, ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
  }
}
