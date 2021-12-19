package silverchain.internal.frontend.core.data;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser;

@API(status = API.Status.INTERNAL)
public class ReturnTypeContext extends AgParser.ReturnTypeContext {

  private Tokens sources = new Tokens();

  public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
    super(parent, invokingState);
  }

  public Tokens sources() {
    return sources;
  }

  public void sources(Tokens sources) {
    this.sources = sources;
  }
}
