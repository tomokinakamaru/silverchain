package silverchain.internal.frontend.checker;

import silverchain.SilverchainException;
import silverchain.internal.frontend.parser.antlr.AgParser.FragmentDeclContext;

public class DuplicateFragment extends SilverchainException {

  protected static final String FORMAT = "Duplicate fragment (L%dC%d and L%dC%d)";

  public DuplicateFragment(FragmentDeclContext ctx1, FragmentDeclContext ctx2) {
    super(
        FORMAT,
        ctx1.start.getLine(),
        ctx1.start.getCharPositionInLine() + 1,
        ctx2.start.getLine(),
        ctx2.start.getCharPositionInLine() + 1);
  }
}
