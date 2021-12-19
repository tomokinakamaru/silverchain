package silverchain.internal.frontend.core.data;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser;

@API(status = API.Status.INTERNAL)
public class MethodContext extends AgParser.MethodContext {

  private Tokens targets = new Tokens();

  public MethodContext(ParserRuleContext parent, int invokingState) {
    super(parent, invokingState);
  }

  public Tokens targets() {
    return targets;
  }

  public void targets(Tokens targets) {
    this.targets = targets;
  }
}
