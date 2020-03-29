package silverchain.graph;

import static java.util.stream.Stream.generate;

import silverchain.grammar.ASTNode;

public final class GraphBuilders {

  private GraphBuilders() {}

  public static Graph atom(ASTNode label) {
    GraphEdge e = new GraphEdge(new GraphLabel(label));
    return new Graph(new GraphNodes(e.source), new GraphNodes(e.destination), new GraphEdges(e));
  }

  public static Graph copy(Graph graph) {
    Tracer<GraphNode> tracer = new Tracer<>(graph.startNodes);
    graph.edges.forEach(e -> tracer.trace(e.source, e.destination, e.label));
    return new Graph(
        tracer.nodes(graph.startNodes::contains),
        tracer.nodes(graph.endNodes::contains),
        tracer.edges());
  }

  public static Graph repeat(Graph graph, Integer min, Integer max) {
    min = min == null ? 0 : min;
    Graph first = generate(() -> graph).limit(min).reduce(GraphBuilders::join).orElse(atom());

    if (max == null) {
      Graph second = copy(graph);
      second.edges.fuse(second.startNodes, second.endNodes);
      second.edges.fuse(second.endNodes, second.startNodes);
      return join(first, second);
    } else {
      Graph g = copy(graph);
      g.edges.fuse(g.startNodes, g.endNodes);
      Graph second = generate(() -> g).limit(max - min).reduce(GraphBuilders::join).orElse(atom());
      return join(first, second);
    }
  }

  public static Graph join(Graph graph1, Graph graph2) {
    graph1 = copy(graph1);
    Graph graph = new Graph();
    graph.startNodes = graph1.startNodes;
    graph.endNodes = graph2.endNodes;
    graph.edges = new GraphEdges(graph1.edges, graph2.edges);
    graph.edges.fuse(graph1.endNodes, graph2.startNodes);
    return graph;
  }

  public static Graph merge(Graph graph1, Graph graph2) {
    graph1 = copy(graph1);
    Graph graph = new Graph();
    graph.startNodes = new GraphNodes(graph1.startNodes, graph2.startNodes);
    graph.endNodes = new GraphNodes(graph1.endNodes, graph2.endNodes);
    graph.edges = new GraphEdges(graph1.edges, graph2.edges);
    return graph;
  }

  private static Graph atom() {
    GraphEdge e = new GraphEdge(null);
    return new Graph(new GraphNodes(e.source), new GraphNodes(e.destination), new GraphEdges(e));
  }
}
