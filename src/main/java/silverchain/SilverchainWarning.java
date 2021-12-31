package silverchain;

import org.apiguardian.api.API;

@API(status = API.Status.EXPERIMENTAL)
public abstract class SilverchainWarning {

  private String message;

  protected SilverchainWarning(String format, Object... args) {
    message = String.format(format, args);
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
