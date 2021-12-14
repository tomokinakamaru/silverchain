package silverchain.internal.frontend.checker;

import static java.lang.Integer.parseInt;

import org.antlr.v4.runtime.Token;
import org.apiguardian.api.API;
import silverchain.internal.frontend.parser.antlr.AgBaseListener;
import silverchain.internal.frontend.parser.antlr.AgParser.RepeatContext;

@API(status = API.Status.INTERNAL)
public class InvalidRangeChecker extends AgBaseListener {

  @Override
  public void enterRepeat(RepeatContext ctx) {
    Token min = ctx.MIN;
    Token max = ctx.MAX;
    if (min != null && max != null && parseInt(min.getText()) > parseInt(max.getText())) {
      throw new InvalidRange(ctx);
    }
  }
}
