package silverchain.parser.adapter;

import static org.apache.commons.lang3.StringUtils.capitalize;

final class Utility {

  static String buildErrorMessage(String message, int line, int col) {
    return capitalize(message) + " (L" + line + "C" + (col + 1) + ")";
  }
}
