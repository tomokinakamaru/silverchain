package silverchain.ag.error;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.ag.data.FragmentRefTree;

@API(status = API.Status.INTERNAL)
public class UndefinedFragment extends SilverchainException {

  public static String FORMAT = "Undefined fragment: %s (%s)";

  public UndefinedFragment(String id, FragmentRefTree t) {
    super(FORMAT, id, t.intervals().first().begin().toString());
  }
}
