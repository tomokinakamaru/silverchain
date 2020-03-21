package silverchain.graph;

public final class GraphEdge {

  GraphNode source;

  GraphNode destination;

  final GraphLabel label;

  GraphEdge(GraphLabel label) {
    this(new GraphNode(), new GraphNode(), label);
  }

  GraphEdge(GraphNode source, GraphNode destination) {
    this(source, destination, null);
  }

  GraphEdge(GraphNode source, GraphNode destination, GraphLabel label) {
    this.source = source;
    this.destination = destination;
    this.label = label;
  }

  public GraphNode source() {
    return source;
  }

  public GraphNode destination() {
    return destination;
  }

  public GraphLabel label() {
    return label;
  }

  void reverse() {
    GraphNode node = source;
    source = destination;
    destination = node;
  }
}
