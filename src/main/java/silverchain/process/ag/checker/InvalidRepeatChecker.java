package silverchain.process.ag.checker;

import org.apiguardian.api.API;
import silverchain.data.ag.visitor.AgTreeChecker;
import silverchain.process.ag.antlr.AgParser.RepeatNMContext;
import silverchain.process.ag.checker.exception.InvalidRepeat;

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
