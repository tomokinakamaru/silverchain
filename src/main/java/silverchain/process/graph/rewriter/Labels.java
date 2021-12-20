package silverchain.process.graph.rewriter;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import org.apiguardian.api.API;
import silverchain.data.graph.Edge;
import silverchain.data.graph.attribute.Label;

@API(status = API.Status.INTERNAL)
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
