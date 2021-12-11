package silverchain.internal.front.validator;

import silverchain.SilverchainException;
import silverchain.internal.front.parser.antlr.AgParser.ImportDeclContext;

public class ImportConflict extends SilverchainException {

  protected static final String FORMAT = "Import conflict (L%dC%d and L%dC%d)";

  public ImportConflict(ImportDeclContext ctx1, ImportDeclContext ctx2) {
    super(
        FORMAT,
        ctx1.start.getLine(),
        ctx1.start.getCharPositionInLine(),
        ctx2.start.getLine(),
        ctx2.start.getLine());
  }
}
