package silverchain.parser.adapter;

import silverchain.SilverchainException;

public final class TokenizeError extends SilverchainException {

  TokenizeError(String msg) {
    super(msg);
  }
}
