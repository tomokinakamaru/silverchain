package silverchain.diagram;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class Labels implements Iterable<Label> {

  private final Set<Label> set = new HashSet<>();

  public void add(Label label) {
    if (label != null) {
      set.add(label);
      set.stream().filter(label::equals).findFirst().ifPresent(l -> l.merge(label));
    }
  }

  @Override
  public Iterator<Label> iterator() {
    return set.iterator();
  }
}
