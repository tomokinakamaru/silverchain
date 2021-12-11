package silverchain.internal.translator.tree;

import silverchain.internal.middle.graph.data.attribute.Label;
import silverchain.internal.middle.graph.data.graph.Edge;
import silverchain.internal.middle.graph.data.graph.Graph;
import silverchain.internal.middle.graph.data.graph.Node;
import silverchain.internal.middle.graph.data.graph.collection.Nodes;

public final class GraphUtils {

  private GraphUtils() {}

  public static Graph atom(Label label) {
    Graph graph = new Graph();
    Node source = new Node();
    Node target = new Node();
    Edge edge = new Edge();
    graph.sources().add(source);
    graph.targets().add(target);
    source.edges().add(edge);
    edge.target(target);
    edge.label(label);
    return graph;
  }

  public static Graph repeat(Graph graph) {
    fuse(graph.targets(), graph.sources());
    return graph;
  }

  public static Graph optional(Graph graph) {
    fuse(graph.sources(), graph.targets());
    return graph;
  }

  public static Graph concat(Graph graph1, Graph graph2) {
    fuse(graph1.targets(), graph2.sources());
    graph1.targets(graph2.targets());
    return graph1;
  }

  public static Graph union(Graph graph1, Graph graph2) {
    graph1.sources().addAll(graph2.sources());
    graph1.targets().addAll(graph2.targets());
    return graph1;
  }

  private static void fuse(Nodes sources, Nodes targets) {
    for (Node source : sources) {
      for (Node target : targets) {
        Edge edge = new Edge();
        edge.target(target);
        source.edges().add(edge);
      }
    }
  }
}
