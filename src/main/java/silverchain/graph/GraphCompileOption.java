package silverchain.graph;

import java.util.List;

public abstract class GraphCompileOption {

  protected abstract boolean equals(GraphLabel label1, GraphLabel label2);

  protected abstract boolean equals(GraphTag tag1, GraphTag tag2);

  protected abstract int compareTo(GraphLabel label1, GraphLabel label2);

  protected abstract List<Object> getTags(GraphLabel label);

  final boolean nullableEquals(GraphLabel label1, GraphLabel label2) {
    if (label1 == null) {
      return label2 == null;
    }
    if (label2 == null) {
      return false;
    }
    return equals(label1, label2);
  }
}
