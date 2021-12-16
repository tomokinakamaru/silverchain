package silverchain.internal.frontend.exception;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.internal.frontend.antlr.AgParser.TypeDeclContext;
import silverchain.internal.frontend.utility.ContextStringifier;

@API(status = API.Status.INTERNAL)
public class DuplicateType extends SilverchainException {

  protected static final String FORMAT = "Duplicate type declaration: %s (L%dC%d and L%dC%d)";

  public DuplicateType(TypeDeclContext ctx1, TypeDeclContext ctx2) {
    super(
        FORMAT,
        ContextStringifier.stringify(ctx1.name()),
        ctx1.start.getLine(),
        ctx1.start.getCharPositionInLine() + 1,
        ctx2.start.getLine(),
        ctx2.start.getCharPositionInLine() + 1);
  }
}
