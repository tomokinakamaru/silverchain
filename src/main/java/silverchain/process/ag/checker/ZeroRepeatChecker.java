package silverchain.process.ag.checker;

import org.apiguardian.api.API;
import silverchain.data.ag.visitor.AgTreeChecker;
import silverchain.process.ag.antlr.AgParser.RepeatNContext;
import silverchain.process.ag.antlr.AgParser.RepeatNMContext;
import silverchain.process.ag.checker.exception.ZeroRepeat;

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
