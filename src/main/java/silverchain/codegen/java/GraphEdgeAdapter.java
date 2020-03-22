package silverchain.codegen.java;

import java.util.ArrayList;
import java.util.List;
import silverchain.grammar.Method;
import silverchain.graph.GraphEdge;

final class GraphEdgeAdapter {

  private final GraphAdapter graph;

  private final GraphEdge edge;

  GraphEdgeAdapter(GraphAdapter graph, GraphEdge edge) {
    this.graph = graph;
    this.edge = edge;
  }

  GraphNodeAdapter destination() {
    return new GraphNodeAdapter(graph, edge.destination());
  }

  Method label() {
    return edge.label().as(Method.class);
  }

  List<String> tags() {
    List<String> tags = new ArrayList<>(destination().tags());
    tags.removeAll(new GraphNodeAdapter(graph, edge.source()).tags());
    return tags;
  }
}
