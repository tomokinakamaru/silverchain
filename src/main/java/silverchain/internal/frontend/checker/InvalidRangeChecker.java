package silverchain.internal.frontend.checker;

import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgBaseListener;
import silverchain.internal.frontend.antlr.AgParser.RepeatNMContext;
import silverchain.internal.frontend.exception.InvalidRange;

@API(status = API.Status.INTERNAL)
public class InvalidRangeChecker extends AgBaseListener {

  @Override
  public void enterRepeatNM(RepeatNMContext ctx) {
    if (Integer.parseInt(ctx.INT(0).getText()) > Integer.parseInt(ctx.INT(1).getText())) {
      throw new InvalidRange(ctx);
    }
  }
}
