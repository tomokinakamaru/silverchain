package silverchain.internal.front.validator;

import silverchain.SilverchainException;
import silverchain.internal.front.parser.antlr.AgParser.FragmentRefContext;

public class UndefinedFragment extends SilverchainException {

  protected static final String FORMAT = "Undefined fragment %s (L%dC%d)";

  public UndefinedFragment(String id, FragmentRefContext ctx) {
    super(FORMAT, id, ctx.start.getLine(), ctx.start.getCharPositionInLine() + 1);
  }
}
