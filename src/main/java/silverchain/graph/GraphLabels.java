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

  GraphLabels distinct(GraphCompileOption option) {
    GraphLabels labels = new GraphLabels();
    for (GraphLabel label : this) {
      if (labels.stream().noneMatch(l -> option.nullableEquals(l, label))) {
        labels.add(label);
      }
    }
    return labels;
  }
}
