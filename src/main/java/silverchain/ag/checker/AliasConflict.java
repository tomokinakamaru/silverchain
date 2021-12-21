package silverchain.ag.checker;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.ag.antlr.AgParser.AliasDeclContext;

@API(status = API.Status.INTERNAL)
public class AliasConflict extends SilverchainException {

  protected static final String FORMAT = "Alias conflict: %s (L%dC%d and L%dC%d)";

  public AliasConflict(AliasDeclContext ctx1, AliasDeclContext ctx2) {
    super(
        FORMAT,
        ctx1.name().ID(),
        ctx1.start.getLine(),
        ctx1.start.getCharPositionInLine() + 1,
        ctx2.start.getLine(),
        ctx2.start.getCharPositionInLine() + 1);
  }
}
