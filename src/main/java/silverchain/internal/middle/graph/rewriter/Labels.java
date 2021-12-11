package silverchain.internal.middle.graph.rewriter;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import silverchain.internal.middle.graph.data.attribute.Label;
import silverchain.internal.middle.graph.data.graph.Edge;

public class Labels implements Iterable<Label> {

  private final Set<Label> labels = new LinkedHashSet<>();

  void add(Edge edge) {
    add(edge.label());
  }

  private void add(Label label) {
    if (label == null) return;
    Label l = find(label);
    if (l == null) labels.add(label);
    else l.locations().addAll(label.locations());
  }

  @Override
  public @Nonnull Iterator<Label> iterator() {
    return labels.iterator();
  }

  private Label find(Label label) {
    return labels.stream().filter(l -> LabelComparator.equals(l, label)).findFirst().orElse(null);
  }
}
