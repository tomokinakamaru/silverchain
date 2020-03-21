package silverchain.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

final class Tracer<T> {

  private final Map<T, GraphNode> nodes = new HashMap<>();

  private final GraphEdges edges = new GraphEdges();

  Tracer(T node) {
    trace(node);
  }

  Tracer(Collection<T> nodes) {
    nodes.forEach(this::trace);
  }

  void trace(T node) {
    if (!nodes.containsKey(node)) {
      nodes.put(node, new GraphNode());
    }
  }

  void trace(T source, T destination, GraphLabel label) {
    trace(source);
    trace(destination);
    edges.add(new GraphEdge(nodes.get(source), nodes.get(destination), label));
  }

  GraphNodes nodes(Predicate<T> predicate) {
    return nodes
        .keySet()
        .stream()
        .filter(predicate)
        .map(nodes::get)
        .collect(Collectors.toCollection(GraphNodes::new));
  }

  GraphEdges edges() {
    return edges;
  }
}
