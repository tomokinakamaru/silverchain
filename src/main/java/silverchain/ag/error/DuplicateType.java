package silverchain.ag.error;

import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.ag.data.TypeDeclTree;

@API(status = API.Status.INTERNAL)
public class DuplicateType extends SilverchainException {

  public static String FORMAT = "Duplicate type declaration: %s (%s and %s)";

  public DuplicateType(String id, TypeDeclTree t1, TypeDeclTree t2) {
    super(
        FORMAT,
        id,
        t1.intervals().get(0).begin().toString(),
        t2.intervals().get(0).begin().toString());
  }
}
