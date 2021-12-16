package silverchain.internal.frontend.checker;

import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgBaseListener;
import silverchain.internal.frontend.antlr.AgParser.RepeatContext;
import silverchain.internal.frontend.exception.ZeroRepeat;

@API(status = API.Status.INTERNAL)
public class ZeroRepeatChecker extends AgBaseListener {

  @Override
  public void enterRepeat(RepeatContext ctx) {
    if (ctx.COMMA == null) {
      if (Integer.parseInt(ctx.MIN.getText()) == 0) {
        throw new ZeroRepeat(ctx);
      }
    } else {
      if (ctx.MAX != null && Integer.parseInt(ctx.MAX.getText()) == 0) {
        throw new ZeroRepeat(ctx);
      }
    }
  }
}
