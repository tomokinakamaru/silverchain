package silverchain.process.ag.builder;

import org.apiguardian.api.API;
import silverchain.SilverchainException;

@API(status = API.Status.INTERNAL)
public class SyntaxError extends SilverchainException {

  protected static final String FORMAT = "Syntax error: %s (L%dC%d)";

  public SyntaxError(int line, int charPosition, String message) {
    super(FORMAT, message, line, charPosition + 1);
  }
}
