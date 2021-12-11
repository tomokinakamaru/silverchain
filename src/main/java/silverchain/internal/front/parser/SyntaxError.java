package silverchain.internal.front.parser;

import silverchain.SilverchainException;

public class SyntaxError extends SilverchainException {

  protected static final String FORMAT = "%s (L%dC%d)";

  public SyntaxError(int line, int column, String message) {
    super(FORMAT, message, line, column);
  }
}
