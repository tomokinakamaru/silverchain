package silverchain.internal.frontend.checker;

import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgBaseListener;
import silverchain.internal.frontend.antlr.AgParser.RepeatNMContext;
import silverchain.internal.frontend.exception.InvalidRange;

@API(status = API.Status.INTERNAL)
public class InvalidRangeChecker extends AgBaseListener {

  @Override
  public void enterRepeatNM(RepeatNMContext ctx) {
    int min = Integer.parseInt(ctx.INT(0).getText());
    int max = Integer.parseInt(ctx.INT(1).getText());
    if (min > max) {
      throw new InvalidRange(ctx);
    }
  }
}
