package silverchain.internal.frontend.checker;

import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgBaseListener;
import silverchain.internal.frontend.antlr.AgParser.RepeatNContext;
import silverchain.internal.frontend.antlr.AgParser.RepeatNMContext;
import silverchain.internal.frontend.exception.ZeroRepeat;

@API(status = API.Status.INTERNAL)
public class ZeroRepeatChecker extends AgBaseListener {

  @Override
  public void enterRepeatN(RepeatNContext ctx) {
    if (Integer.parseInt(ctx.INT().getText()) == 0) {
      throw new ZeroRepeat(ctx);
    }
  }

  @Override
  public void enterRepeatNM(RepeatNMContext ctx) {
    if (Integer.parseInt(ctx.INT(1).getText()) == 0) {
      throw new ZeroRepeat(ctx);
    }
  }
}
