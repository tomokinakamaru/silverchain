package silverchain.ag.error;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.ag.data.FragmentDeclTree;

@API(status = API.Status.INTERNAL)
public class DuplicateFragment extends SilverchainException {

  public static String FORMAT = "Duplicate fragment declaration: %s (%s and %s)";

  public DuplicateFragment(FragmentDeclTree t1, FragmentDeclTree t2) {
    super(
        FORMAT,
        t1.id(),
        t1.intervals().get(0).begin().toString(),
        t2.intervals().get(0).begin().toString());
  }
}
