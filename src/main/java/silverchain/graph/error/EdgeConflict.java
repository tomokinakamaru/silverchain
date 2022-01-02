package silverchain.graph.error;

import java.util.Set;
import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.graph.data.EdgeAttr;

@API(status = API.Status.INTERNAL)
public class EdgeConflict extends SilverchainException {

  protected static final String FORMAT = "Conflict: %s";

  public EdgeConflict(Set<EdgeAttr> attributes) {
    super(FORMAT, attributes.toString());
  }
}
