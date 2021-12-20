package silverchain.process.ag.checker.exception;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.process.ag.antlr.AgParser.TypeDeclContext;

@API(status = API.Status.INTERNAL)
public class DuplicateType extends SilverchainException {

  protected static final String FORMAT = "Duplicate type declaration: %s (L%dC%d and L%dC%d)";

  public DuplicateType(String id, TypeDeclContext ctx1, TypeDeclContext ctx2) {
    super(
        FORMAT,
        id,
        ctx1.start.getLine(),
        ctx1.start.getCharPositionInLine() + 1,
        ctx2.start.getLine(),
        ctx2.start.getCharPositionInLine() + 1);
  }
}
