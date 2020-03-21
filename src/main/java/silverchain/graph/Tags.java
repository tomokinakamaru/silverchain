package silverchain.graph;

import java.util.Collection;
import java.util.LinkedHashSet;

final class Tags extends LinkedHashSet<String> {

  Tags() {}

  Tags(Collection<String> tags1, Collection<String> tags2) {
    addAll(tags1);
    addAll(tags2);
  }
}
