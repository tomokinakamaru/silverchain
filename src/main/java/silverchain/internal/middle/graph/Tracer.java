package silverchain.internal.middle.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import silverchain.internal.middle.graph.data.attribute.Label;
import silverchain.internal.middle.graph.data.graph.Edge;
import silverchain.internal.middle.graph.data.graph.Node;
import silverchain.internal.middle.graph.data.graph.collection.Nodes;

public class Tracer<T> {

  protected final Map<T, Node> map = new HashMap<>();

  public void trace(T object) {
    map.computeIfAbsent(object, o -> new Node());
  }

  public void trace(T source, T target, Label label) {
    trace(source);
    trace(target);
    Edge edge = new Edge();
    edge.label(label);
    edge.target(map.get(target));
    map.get(source).edges().add(edge);
  }

  public Nodes find(Predicate<T> predicate) {
    return map.entrySet().stream()
        .filter(entry -> predicate.test(entry.getKey()))
        .map(Map.Entry::getValue)
        .collect(Collectors.toCollection(Nodes::new));
  }
}
