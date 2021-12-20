package silverchain;

import org.apiguardian.api.API;

@API(status = API.Status.EXPERIMENTAL)
public abstract class SilverchainException extends RuntimeException {

  protected SilverchainException(String format, Object... args) {
    super(String.format(format, args));
  }
}
