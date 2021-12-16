package silverchain.internal.frontend.checker;

import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgBaseListener;
import silverchain.internal.frontend.antlr.AgParser.RepeatContext;
import silverchain.internal.frontend.exception.InvalidRange;

@API(status = API.Status.INTERNAL)
public class InvalidRangeChecker extends AgBaseListener {

  @Override
  public void enterRepeat(RepeatContext ctx) {
    if (ctx.MAX != null) {
      if (Integer.parseInt(ctx.MIN.getText()) > Integer.parseInt(ctx.MAX.getText())) {
        throw new InvalidRange(ctx);
      }
    }
  }
}
