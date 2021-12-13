package silverchain.internal.middleware.graph.data;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import silverchain.internal.middleware.graph.data.graph.Edge;
import silverchain.internal.middleware.graph.data.graph.Graph;
import silverchain.internal.middleware.graph.data.graph.Node;
import silverchain.internal.middleware.graph.data.graph.collection.Graphs;
import silverchain.internal.middleware.graph.data.graph.collection.Nodes;

public class GraphWalker {

  protected Queue<Entry<Node, Edge>> queue;

  protected Set<Node> visited;

  public void walk(GraphListener listener, Graphs graphs) {
    listener.enter(graphs);
    graphs.forEach(graph -> walk(listener, graph));
    listener.exit(graphs);
  }

  public void walk(GraphListener listener, Graph graph) {
    queue = new LinkedList<>();
    visited = new HashSet<>();
    listener.enter(graph);
    visitIfNotVisited(listener, graph, graph.sources());
    while (!queue.isEmpty()) {
      Entry<Node, Edge> entry = queue.remove();
      Edge edge = entry.getValue();
      Node source = entry.getKey();
      Node target = edge.target();
      listener.visit(graph, source, edge);
      visitIfNotVisited(listener, graph, target);
    }
    listener.exit(graph);
  }

  protected void visitIfNotVisited(GraphListener listener, Graph graph, Nodes nodes) {
    nodes.stream()
        .sorted(listener.nodeComparator())
        .forEach(node -> visitIfNotVisited(listener, graph, node));
  }

  protected void visitIfNotVisited(GraphListener listener, Graph graph, Node node) {
    if (!visited.contains(node)) {
      listener.visit(graph, node);
      visited.add(node);
      enqueueEdges(listener, node);
    }
  }

  protected void enqueueEdges(GraphListener listener, Node node) {
    node.edges().stream()
        .filter(listener.edgeFilter())
        .sorted(listener.edgeComparator())
        .map(e -> new SimpleEntry<>(node, e))
        .forEach(queue::add);
  }
}
