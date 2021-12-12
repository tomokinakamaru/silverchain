package silverchain.internal.middle.graph.validator;

import java.util.List;
import java.util.stream.Collectors;
import silverchain.SilverchainException;
import silverchain.internal.middle.data.attribute.Label;

public class EdgeConflict extends SilverchainException {

  protected static final String FORMAT = "Conflict: %s";

  public EdgeConflict(List<Label> labels) {
    super(FORMAT, stringify(labels));
  }

  protected static String stringify(List<Label> labels) {
    return labels.stream()
        .map(l -> l.accept(new LabelStringifier(), null))
        .collect(Collectors.joining("; "));
  }
}
