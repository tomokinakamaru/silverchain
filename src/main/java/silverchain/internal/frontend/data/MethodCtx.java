package silverchain.internal.frontend.data;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.MethodContext;

@API(status = API.Status.INTERNAL)
public class MethodCtx extends MethodContext {

  private Tokens targets = new Tokens();

  public MethodCtx(ParserRuleContext parent, int invokingState) {
    super(parent, invokingState);
  }

  public Tokens targets() {
    return targets;
  }

  public void targets(Tokens targets) {
    this.targets = targets;
  }
}
