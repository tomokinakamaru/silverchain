package silverchain.generator;

public abstract class EncodeError extends RuntimeException {

  protected EncodeError(String message) {
    super(message);
  }
}
