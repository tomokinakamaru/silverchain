package silverchain.ag.error;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.ag.data.RangeNMTree;

@API(status = API.Status.INTERNAL)
public class InvalidRange extends SilverchainException {

  public static String FORMAT = "Invalid range: min=%s > max=%s (%s)";

  public InvalidRange(RangeNMTree t) {
    super(FORMAT, t.min(), t.max(), t.intervals().first().begin().toString());
  }
}
