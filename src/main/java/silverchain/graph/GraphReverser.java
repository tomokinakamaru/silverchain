package silverchain.graph;

import org.apiguardian.api.API;
import silverchain.graph.data.Edge;
import silverchain.graph.data.Graph;
import silverchain.graph.data.Vertex;
import silverchain.graph.data.Vertices;
import silverchain.graph.walker.GraphListener;

@API(status = API.Status.INTERNAL)
public class GraphReverser implements GraphListener {

  protected Tracer<Vertex> tracer = new Tracer<>();

  @Override
  public void visit(Graph graph, Vertex vertex) {
    tracer.trace(vertex);
  }

  @Override
  public void visit(Graph graph, Vertex source, Edge edge) {
    tracer.trace(edge.target(), source, edge.attr());
  }

  @Override
  public void exit(Graph graph) {
    Vertices sources = tracer.find(graph.targets()::contains);
    Vertices targets = tracer.find(graph.sources()::contains);
    graph.sources(sources);
    graph.targets(targets);
  }
}
