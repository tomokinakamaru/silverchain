package silverchain.internal.frontend.checker;

import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.RepeatNContext;
import silverchain.internal.frontend.antlr.AgParser.RepeatNMContext;
import silverchain.internal.frontend.checker.exception.ZeroRepeat;
import silverchain.internal.frontend.core.AgTreeChecker;

@API(status = API.Status.INTERNAL)
public class ZeroRepeatChecker extends AgTreeChecker {

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
