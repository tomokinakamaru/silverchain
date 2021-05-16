package silverchain.validator;

import silverchain.SilverchainException;

public final class ValidationError extends SilverchainException {

  public ValidationError(String format, Object... args) {
    super(format, args);
  }
}
