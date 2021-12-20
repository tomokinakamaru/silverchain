package silverchain;

import org.apiguardian.api.API;

@API(status = API.Status.EXPERIMENTAL)
public abstract class SilverchainWarning {

  private String message;

  protected SilverchainWarning(String format, Object... args) {
    message = String.format(format, args);
  }

  public String message() {
    return message;
  }

  public void message(String message) {
    this.message = message;
  }
}
