package silverchain.generator;

import silverchain.SilverchainException;

public final class EncodeError extends SilverchainException {

  EncodeError(String format, Object... args) {
    super(format, args);
  }
}
