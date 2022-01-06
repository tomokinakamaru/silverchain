package silverchain.ag.error;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.ag.data.RangeTree;

@API(status = API.Status.INTERNAL)
public class ZeroRepeat extends SilverchainException {

  public static String FORMAT = "Max is zero (%s)";

  public ZeroRepeat(RangeTree t) {
    super(FORMAT, t.srcMap().get(0).begin().toString());
  }
}
