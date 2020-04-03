package silverchain.graph;

import java.util.LinkedHashSet;
import silverchain.parser.TypeParameter;

final class GraphTags extends LinkedHashSet<GraphTag> {

  GraphTags() {}

  GraphTags(GraphTags tags1, Iterable<TypeParameter> tags2) {
    addAll(tags1);
    tags2.forEach(t -> add(new GraphTag(t)));
  }
}
