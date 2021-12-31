package silverchain.graph;

import java.util.function.Predicate;
import org.apiguardian.api.API;
import silverchain.graph.data.Edge;
import silverchain.graph.data.Graph;
import silverchain.graph.data.Vertex;
import silverchain.graph.data.Vertices;
import silverchain.graph.walker.GraphListener;
import silverchain.graph.walker.GraphWalker;

@API(status = API.Status.INTERNAL)
public final class EpsClosureFinder implements GraphListener {

  private final Vertices vertices = new Vertices();

  private EpsClosureFinder() {}

  public static Vertices find(Vertices vertices) {
    EpsClosureFinder finder = new EpsClosureFinder();
    Graph graph = new Graph();
    graph.sources(vertices);
    GraphWalker.walk(graph, finder);
    return finder.vertices;
  }

  @Override
  public void visit(Graph graph, Vertex vertex) {
    vertices.add(vertex);
  }

  @Override
  public Predicate<Edge> edgeFilter() {
    return edge -> edge.attr() == null;
  }
}
