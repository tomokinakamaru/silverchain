package silverchain.ag;

import org.apiguardian.api.API;
import silverchain.ag.antlr.AgBaseListener;
import silverchain.ag.antlr.AgParser;

@API(status = API.Status.INTERNAL)
public abstract class AgListener extends AgBaseListener {

  public void enterReturnType(ReturnTypeContext ctx) {}

  public void exitReturnType(ReturnTypeContext ctx) {}

  public void enterMethod(MethodContext ctx) {}

  public void exitMethod(MethodContext ctx) {}

  @Override
  public final void enterReturnType(AgParser.ReturnTypeContext ctx) {
    enterReturnType((ReturnTypeContext) ctx);
  }

  @Override
  public final void exitReturnType(AgParser.ReturnTypeContext ctx) {
    exitReturnType((ReturnTypeContext) ctx);
  }

  @Override
  public final void enterMethod(AgParser.MethodContext ctx) {
    enterMethod((MethodContext) ctx);
  }

  @Override
  public final void exitMethod(AgParser.MethodContext ctx) {
    exitMethod((MethodContext) ctx);
  }
}
