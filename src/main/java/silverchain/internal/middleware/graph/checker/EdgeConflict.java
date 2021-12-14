package silverchain.internal.middleware.graph.checker;

import java.util.List;
import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.SilverchainException;
import silverchain.internal.middleware.graph.data.attribute.Label;

@API(status = API.Status.INTERNAL)
public class EdgeConflict extends SilverchainException {

  protected static final String FORMAT = "Conflict: %s";

  public EdgeConflict(List<Label> labels) {
    super(FORMAT, stringify(labels));
  }

  protected static String stringify(List<Label> labels) {
    LabelStringifier stringifier = new LabelStringifier();
    return labels.stream().map(l -> l.accept(stringifier, null)).collect(Collectors.joining("; "));
  }
}
