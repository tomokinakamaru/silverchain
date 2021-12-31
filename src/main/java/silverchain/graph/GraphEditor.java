package silverchain.graph;

import org.apiguardian.api.API;
import silverchain.graph.data.Edge;
import silverchain.graph.data.EdgeAttr;
import silverchain.graph.data.Graph;
import silverchain.graph.data.Vertex;
import silverchain.graph.data.Vertices;

@API(status = API.Status.INTERNAL)
public final class GraphEditor {

  private GraphEditor() {}

  public static Graph atom(EdgeAttr attr) {
    Graph graph = new Graph();
    Vertex source = new Vertex();
    Vertex target = new Vertex();
    Edge edge = new Edge();
    graph.sources().add(source);
    graph.targets().add(target);
    source.edges().add(edge);
    edge.target(target);
    edge.attr(attr);
    return graph;
  }

  public static Graph oneMore(Graph graph) {
    fuse(graph.targets(), graph.sources());
    return graph;
  }

  public static Graph zeroOne(Graph graph) {
    fuse(graph.sources(), graph.targets());
    return graph;
  }

  public static Graph zeroMore(Graph graph) {
    return zeroOne(oneMore(graph));
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

  public static void fuse(Vertices sources, Vertices targets) {
    for (Vertex source : sources) {
      for (Vertex target : targets) {
        Edge edge = new Edge();
        edge.target(target);
        source.edges().add(edge);
      }
    }
  }
}
