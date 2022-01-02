package silverchain.ag.error;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.ag.data.AliasDeclTree;

@API(status = API.Status.INTERNAL)
public class AliasConflict extends SilverchainException {

  public static String FORMAT = "Alias conflict: %s (%s and %s)";

  public AliasConflict(AliasDeclTree t1, AliasDeclTree t2) {
    super(
        FORMAT,
        t1.name().id(),
        t1.srcMap().get(0).begin().toString(),
        t2.srcMap().get(0).begin().toString());
  }
}
