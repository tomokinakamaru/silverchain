package silverchain.generator;

import silverchain.SilverchainException;

public abstract class EncodeError extends SilverchainException {

  protected EncodeError(String format, Object... args) {
    super(format, args);
  }
}
