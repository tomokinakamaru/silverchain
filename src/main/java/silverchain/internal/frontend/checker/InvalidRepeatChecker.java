package silverchain.internal.frontend.checker;

import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.RepeatNMContext;
import silverchain.internal.frontend.checker.exception.InvalidRepeat;
import silverchain.internal.frontend.core.AgTreeChecker;

@API(status = API.Status.INTERNAL)
public class InvalidRepeatChecker extends AgTreeChecker {

  @Override
  public void enterRepeatNM(RepeatNMContext ctx) {
    int min = Integer.parseInt(ctx.INT(0).getText());
    int max = Integer.parseInt(ctx.INT(1).getText());
    if (min > max) {
      throw new InvalidRepeat(ctx);
    }
  }
}
