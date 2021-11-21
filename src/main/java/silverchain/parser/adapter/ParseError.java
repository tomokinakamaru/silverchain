package silverchain.parser.adapter;

import silverchain.SilverchainException;

public final class ParseError extends SilverchainException {

  ParseError(String msg) {
    super(msg);
  }
}
