package silverchain.internal.frontend.exception;

import org.apiguardian.api.API;
import silverchain.SilverchainException;

@API(status = API.Status.INTERNAL)
public class SyntaxError extends SilverchainException {

  protected static final String FORMAT = "Syntax error: %s (L%dC%d)";

  public SyntaxError(int line, int charPosition, String message) {
    super(FORMAT, capitalize(message), line, charPosition + 1);
  }

  protected static String capitalize(String message) {
    return message.substring(0, 1).toUpperCase() + message.substring(1);
  }
}
