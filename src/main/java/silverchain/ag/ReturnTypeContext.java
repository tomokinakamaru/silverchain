package silverchain.ag;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apiguardian.api.API;
import silverchain.ag.antlr.AgParser;

@API(status = API.Status.INTERNAL)
public class ReturnTypeContext extends AgParser.ReturnTypeContext {

  private String name;

  public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
    super(parent, invokingState);
  }

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }
}
