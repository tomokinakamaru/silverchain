package silverchain.internal.frontend.data;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.ReturnTypeContext;

@API(status = API.Status.INTERNAL)
public class ReturnTypeCtx extends ReturnTypeContext {

  private Tokens sources = new Tokens();

  public ReturnTypeCtx(ParserRuleContext parent, int invokingState) {
    super(parent, invokingState);
  }

  public Tokens sources() {
    return sources;
  }

  public void sources(Tokens sources) {
    this.sources = sources;
  }
}
