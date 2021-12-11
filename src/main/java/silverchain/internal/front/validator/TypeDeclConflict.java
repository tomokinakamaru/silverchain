package silverchain.internal.front.validator;

import silverchain.SilverchainException;
import silverchain.internal.front.parser.antlr.AgParser.TypeDeclContext;

public class TypeDeclConflict extends SilverchainException {

  protected static final String FORMAT = "Type declaration conflict (L%dC%d and L%dC%d)";

  public TypeDeclConflict(TypeDeclContext ctx1, TypeDeclContext ctx2) {
    super(
        FORMAT,
        ctx1.start.getLine(),
        ctx1.start.getCharPositionInLine(),
        ctx2.start.getLine(),
        ctx2.start.getLine());
  }
}
