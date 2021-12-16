package silverchain.internal.frontend.exception;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.internal.frontend.antlr.AgParser.RepeatContext;

@API(status = API.Status.INTERNAL)
public class InvalidRange extends SilverchainException {

  protected static final String FORMAT = "min=%s > max=%s (L%dC%d)";

  public InvalidRange(RepeatContext ctx) {
    super(
        FORMAT,
        ctx.MIN.getText(),
        ctx.MAX.getText(),
        ctx.start.getLine(),
        ctx.start.getCharPositionInLine() + 1);
  }
}
