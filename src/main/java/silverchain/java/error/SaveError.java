package silverchain.java.error;

import java.nio.file.Path;
import org.apiguardian.api.API;
import silverchain.SilverchainException;

@API(status = API.Status.INTERNAL)
public class SaveError extends SilverchainException {

  protected static final String FORMAT = "Save failure: %s";

  public SaveError(Path path) {
    super(FORMAT, path.toString());
  }
}
