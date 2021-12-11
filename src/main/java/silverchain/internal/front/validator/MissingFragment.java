package silverchain.internal.front.validator;

import silverchain.SilverchainException;
import silverchain.internal.front.parser.AgParser.FragmentRefContext;

public class MissingFragment extends SilverchainException {

  protected static final String FORMAT = "L%dC%d and L%dC%d";

  public MissingFragment(String id, FragmentRefContext ctx) {
    super(FORMAT, id, ctx.start.getLine(), ctx.start.getCharPositionInLine());
  }
}
