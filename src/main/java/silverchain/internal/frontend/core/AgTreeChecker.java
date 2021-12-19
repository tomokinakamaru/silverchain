package silverchain.internal.frontend.core;

import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgBaseListener;
import silverchain.internal.frontend.antlr.AgParser;
import silverchain.internal.frontend.core.data.MethodContext;
import silverchain.internal.frontend.core.data.ReturnTypeContext;

@API(status = API.Status.INTERNAL)
public abstract class AgTreeChecker extends AgBaseListener {

  public void enterReturnType(ReturnTypeContext ctx) {}

  public void exitReturnType(ReturnTypeContext ctx) {}

  public void enterMethod(MethodContext ctx) {}

  public void exitMethod(MethodContext ctx) {}

  @Override
  public void enterReturnType(AgParser.ReturnTypeContext ctx) {
    enterReturnType((ReturnTypeContext) ctx);
  }

  @Override
  public void exitReturnType(AgParser.ReturnTypeContext ctx) {
    exitReturnType((ReturnTypeContext) ctx);
  }

  @Override
  public void enterMethod(AgParser.MethodContext ctx) {
    enterMethod((MethodContext) ctx);
  }

  @Override
  public void exitMethod(AgParser.MethodContext ctx) {
    exitMethod((MethodContext) ctx);
  }
}
