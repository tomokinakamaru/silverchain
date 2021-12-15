package silverchain.internal.frontend.checker;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.internal.frontend.parser.antlr.AgParser.ImportDeclContext;

@API(status = API.Status.INTERNAL)
public class ImportConflict extends SilverchainException {

  protected static final String FORMAT = "Import conflict: %s (L%dC%d and L%dC%d)";

  public ImportConflict(ImportDeclContext ctx1, ImportDeclContext ctx2) {
    super(
        FORMAT,
        ctx1.name().ID(),
        ctx1.start.getLine(),
        ctx1.start.getCharPositionInLine() + 1,
        ctx2.start.getLine(),
        ctx2.start.getCharPositionInLine() + 1);
  }
}
