package silverchain.internal.front.validator;

import silverchain.SilverchainException;
import silverchain.internal.front.parser.antlr.AgParser.FragmentDeclContext;

public class FragmentConflict extends SilverchainException {

  protected static final String FORMAT = "Fragment conflict (L%dC%d and L%dC%d)";

  public FragmentConflict(FragmentDeclContext ctx1, FragmentDeclContext ctx2) {
    super(
        FORMAT,
        ctx1.start.getLine(),
        ctx1.start.getCharPositionInLine(),
        ctx2.start.getLine(),
        ctx2.start.getLine());
  }
}
