package silverchain.data.ag.visitor;

import org.apiguardian.api.API;
import silverchain.data.ag.MethodContext;
import silverchain.data.ag.ReturnTypeContext;
import silverchain.process.ag.antlr.AgBaseListener;
import silverchain.process.ag.antlr.AgParser;

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
