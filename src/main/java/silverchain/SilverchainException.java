package silverchain;

public abstract class SilverchainException extends RuntimeException {

  protected SilverchainException(String format, Object... args) {
    super(String.format(format, args));
  }
}
