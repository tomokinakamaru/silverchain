package silverchain.graph;

import java.util.LinkedHashSet;

final class GraphTags extends LinkedHashSet<GraphTag> {

  GraphTags() {}

  GraphTags(GraphTags tags) {
    addAll(tags);
  }
}
