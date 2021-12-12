package silverchain.internal.front.validator;

import silverchain.SilverchainException;
import silverchain.internal.front.parser.antlr.AgParser.TypeDeclContext;

public class DuplicateTypeDecl extends SilverchainException {

  protected static final String FORMAT = "Duplicate type declaration (L%dC%d and L%dC%d)";

  public DuplicateTypeDecl(TypeDeclContext ctx1, TypeDeclContext ctx2) {
    super(
        FORMAT,
        ctx1.start.getLine(),
        ctx1.start.getCharPositionInLine() + 1,
        ctx2.start.getLine(),
        ctx2.start.getCharPositionInLine() + 1);
  }
}
