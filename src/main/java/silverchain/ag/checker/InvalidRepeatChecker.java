package silverchain.ag.checker;

import org.apiguardian.api.API;
import silverchain.ag.AgListener;
import silverchain.ag.antlr.AgParser.RepeatNMContext;

@API(status = API.Status.INTERNAL)
public class InvalidRepeatChecker extends AgListener {

  @Override
  public void enterRepeatNM(RepeatNMContext ctx) {
    int min = Integer.parseInt(ctx.INT(0).getText());
    int max = Integer.parseInt(ctx.INT(1).getText());
    if (min > max) {
      throw new InvalidRepeat(ctx);
    }
  }
}
