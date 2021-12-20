package silverchain.process.ag.checker.exception;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.process.ag.antlr.AgParser.RepeatNContext;
import silverchain.process.ag.antlr.AgParser.RepeatNMContext;

@API(status = API.Status.INTERNAL)
public class ZeroRepeat extends SilverchainException {

  protected static final String FORMAT = "Max is zero (L%dC%d)";

  public ZeroRepeat(RepeatNContext ctx) {
    super(FORMAT, ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
  }

  public ZeroRepeat(RepeatNMContext ctx) {
    super(FORMAT, ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
  }
}
