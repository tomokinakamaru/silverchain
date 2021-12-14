package silverchain.internal.frontend.checker;

import org.apiguardian.api.API;
import silverchain.internal.frontend.parser.antlr.AgBaseListener;
import silverchain.internal.frontend.parser.antlr.AgParser.RepeatSugarContext;

@API(status = API.Status.INTERNAL)
public class ZeroRepeatChecker extends AgBaseListener {

  @Override
  public void enterRepeatSugar(RepeatSugarContext ctx) {
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
