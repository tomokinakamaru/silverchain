package silverchain.java.error;

import org.apiguardian.api.API;
import silverchain.SilverchainException;

@API(status = API.Status.INTERNAL)
public class FileCountExcess extends SilverchainException {

  protected static final String FORMAT = "";

  public FileCountExcess(int n) {
    super(FORMAT);
  }
}
