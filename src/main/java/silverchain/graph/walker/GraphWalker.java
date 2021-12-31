package silverchain.graph.walker;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.data.Edge;
import silverchain.graph.data.Graph;
import silverchain.graph.data.Graphs;
import silverchain.graph.data.Vertex;
import silverchain.graph.data.Vertices;

@API(status = API.Status.INTERNAL)
public final class GraphWalker {

  private GraphWalker() {}

  public static void walk(Graphs graphs, GraphListener listener) {
    listener.enter(graphs);
    for (Graph graph : graphs) {
      listener.enter(graph);
      walk(graph, listener);
      listener.exit(graph);
    }
    listener.exit(graphs);
  }

  public static void walk(Graph graph, GraphListener listener) {
    Queue<Entry> queue = new LinkedList<>();
    Set<Vertex> visited = new HashSet<>(graph.sources());
    visit(listener, graph, graph.sources()).forEach(queue::add);
    while (!queue.isEmpty()) {
      Entry entry = queue.remove();
      Edge edge = entry.getValue();
      Vertex source = entry.getKey();
      Vertex target = edge.target();
      listener.visit(graph, source, edge);
      if (!visited.contains(target) && listener.vertexFilter().test(target)) {
        visited.add(target);
        visit(listener, graph, target).forEach(queue::add);
      }
    }
  }

  private static Stream<Entry> visit(GraphListener listener, Graph graph, Vertices vertices) {
    return vertices.stream()
        .filter(listener.vertexFilter())
        .sorted(listener.vertexComparator())
        .flatMap(vertex -> visit(listener, graph, vertex));
  }

  private static Stream<Entry> visit(GraphListener listener, Graph graph, Vertex vertex) {
    listener.visit(graph, vertex);
    return vertex.edges().stream()
        .filter(listener.edgeFilter())
        .sorted(listener.edgeComparator())
        .map(e -> new Entry(vertex, e));
  }

  private static final class Entry extends SimpleEntry<Vertex, Edge> {
    private Entry(Vertex key, Edge value) {
      super(key, value);
    }
  }
}
