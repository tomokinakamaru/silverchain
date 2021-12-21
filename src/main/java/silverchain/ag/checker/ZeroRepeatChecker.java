package silverchain.ag.checker;

import org.apiguardian.api.API;
import silverchain.ag.AgListener;
import silverchain.ag.antlr.AgParser.RepeatNContext;
import silverchain.ag.antlr.AgParser.RepeatNMContext;

@API(status = API.Status.INTERNAL)
public class ZeroRepeatChecker extends AgListener {

  protected static final String ZERO = "0";

  @Override
  public void enterRepeatN(RepeatNContext ctx) {
    if (ctx.INT().getText().equals(ZERO)) {
      throw new ZeroRepeat(ctx);
    }
  }

  @Override
  public void enterRepeatNM(RepeatNMContext ctx) {
    if (ctx.INT(1).getText().equals(ZERO)) {
      throw new ZeroRepeat(ctx);
    }
  }
}
