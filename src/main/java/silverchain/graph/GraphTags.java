package silverchain.graph;

import java.util.LinkedHashSet;
import java.util.List;

final class GraphTags extends LinkedHashSet<GraphTag> {

  GraphTags() {}

  GraphTags(GraphTags tags1, List<Object> tags2) {
    addAll(tags1);
    tags2.stream().map(GraphTag::new).forEach(this::add);
  }
}
