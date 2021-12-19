package silverchain.internal.frontend.checker.exception;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.internal.frontend.antlr.AgParser.FragmentRefContext;

@API(status = API.Status.INTERNAL)
public class UndefinedFragment extends SilverchainException {

  protected static final String FORMAT = "Undefined fragment: %s (L%dC%d)";

  public UndefinedFragment(String id, FragmentRefContext ctx) {
    super(FORMAT, id, ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
  }
}
