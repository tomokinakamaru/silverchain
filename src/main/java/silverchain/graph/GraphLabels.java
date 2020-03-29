package silverchain.graph;

import java.util.LinkedHashSet;

final class GraphLabels extends LinkedHashSet<GraphLabel> {

  @Override
  public boolean add(GraphLabel label) {
    if (label == null) {
      return false;
    }
    return super.add(label);
  }
}
