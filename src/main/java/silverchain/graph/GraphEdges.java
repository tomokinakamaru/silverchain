package silverchain.graph;

import java.util.HashSet;
import java.util.stream.Collectors;

final class GraphEdges extends HashSet<GraphEdge> {

  GraphEdges() {}

  GraphEdges(GraphEdge edge) {
    add(edge);
  }

  GraphEdges(GraphEdges edges1, GraphEdges edges2) {
    addAll(edges1);
    addAll(edges2);
  }

  void fuse(GraphNodes sources, GraphNodes destinations) {
    sources.forEach(s -> destinations.forEach(d -> add(new GraphEdge(s, d))));
  }

  void reverse() {
    forEach(GraphEdge::reverse);
  }

  GraphEdges from(GraphNode node) {
    return stream().filter(e -> e.source == node).collect(Collectors.toCollection(GraphEdges::new));
  }
}
