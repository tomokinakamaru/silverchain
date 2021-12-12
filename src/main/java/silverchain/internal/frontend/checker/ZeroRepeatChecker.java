package silverchain.internal.frontend.checker;

import static java.lang.Integer.parseInt;

import org.apiguardian.api.API;
import silverchain.internal.frontend.parser.antlr.AgBaseListener;
import silverchain.internal.frontend.parser.antlr.AgParser.RepeatSugarContext;

@API(status = API.Status.INTERNAL)
public class ZeroRepeatChecker extends AgBaseListener {

  @Override
  public void enterRepeatSugar(RepeatSugarContext ctx) {
    if (ctx.COMMA != null && ctx.MAX != null && parseInt(ctx.MAX.getText()) == 0) {
      throw new ZeroRepeat(ctx);
    }
  }
}
