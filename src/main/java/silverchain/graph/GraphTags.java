package silverchain.graph;

import java.util.LinkedHashSet;
import java.util.List;

final class GraphTags extends LinkedHashSet<GraphTag> {

  GraphTags() {}

  GraphTags(GraphTags tags1, List<Object> tags2) {
    addAll(tags1);
    tags2.stream().map(GraphTag::new).forEach(this::add);
  }

  GraphTags distinct(GraphCompileOption option) {
    GraphTags tags = new GraphTags();
    for (GraphTag tag : this) {
      if (tags.stream().noneMatch(t -> option.equals(t, tag))) {
        tags.add(tag);
      }
    }
    return tags;
  }
}
