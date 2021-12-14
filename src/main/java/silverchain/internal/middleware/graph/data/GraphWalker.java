package silverchain.internal.middleware.graph.data;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.internal.middleware.graph.data.graph.Edge;
import silverchain.internal.middleware.graph.data.graph.Graph;
import silverchain.internal.middleware.graph.data.graph.Node;
import silverchain.internal.middleware.graph.data.graph.collection.Graphs;
import silverchain.internal.middleware.graph.data.graph.collection.Nodes;

@API(status = API.Status.INTERNAL)
public class GraphWalker {

  public void walk(GraphListener listener, Graphs graphs) {
    listener.enter(graphs);
    for (Graph graph : graphs) {
      listener.enter(graph);
      walk(listener, graph);
      listener.exit(graph);
    }
    listener.exit(graphs);
  }

  protected void walk(GraphListener listener, Graph graph) {
    Queue<Entry> queue = new LinkedList<>();
    Set<Node> visited = new HashSet<>(graph.sources());
    visit(listener, graph, graph.sources()).forEach(queue::add);
    while (!queue.isEmpty()) {
      Entry entry = queue.remove();
      Edge edge = entry.getValue();
      Node source = entry.getKey();
      Node target = edge.target();
      listener.visit(graph, source, edge);
      if (!visited.contains(target) && listener.nodeFilter().test(target)) {
        visited.add(target);
        visit(listener, graph, target).forEach(queue::add);
      }
    }
  }

  protected Stream<Entry> visit(GraphListener listener, Graph graph, Nodes nodes) {
    return nodes.stream()
        .filter(listener.nodeFilter())
        .sorted(listener.nodeComparator())
        .flatMap(node -> visit(listener, graph, node));
  }

  protected Stream<Entry> visit(GraphListener listener, Graph graph, Node node) {
    listener.visit(graph, node);
    return node.edges().stream()
        .filter(listener.edgeFilter())
        .sorted(listener.edgeComparator())
        .map(e -> new Entry(node, e));
  }

  protected static class Entry extends SimpleEntry<Node, Edge> {
    protected Entry(Node node, Edge edge) {
      super(node, edge);
    }
  }
}
