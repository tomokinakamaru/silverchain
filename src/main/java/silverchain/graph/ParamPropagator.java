package silverchain.graph;

import org.apiguardian.api.API;
import silverchain.graph.data.Edge;
import silverchain.graph.data.Edges;
import silverchain.graph.data.Graph;
import silverchain.graph.data.TypeParams;
import silverchain.graph.data.Vertex;
import silverchain.graph.walker.GraphListener;

@API(status = API.Status.INTERNAL)
public class ParamPropagator implements GraphListener {

  @Override
  public void enter(Graph graph) {
    for (Vertex vertex : graph.sources()) {
      vertex.params(new TypeParams());
      vertex.params().addAll(graph.type().outerParams());
    }
  }

  @Override
  public void visit(Graph graph, Vertex vertex) {
    Edges edges = new Edges();
    edges.addAll(vertex.edges());
    for (Edge edge : edges) propagate(graph, vertex, edge);
  }

  protected void propagate(Graph graph, Vertex source, Edge edge) {
    Vertex target = edge.target();
    TypeParams params = targetParams(source, edge);
    if (target.params() == null) {
      target.params(params);
    } else if (!target.params().equals(params)) {
      Vertex copy = copy(graph, edge.target());
      copy.params(params);
      Edge e = new Edge();
      e.target(copy);
      e.attr(edge.attr());
      source.edges().remove(edge);
      source.edges().add(e);
    }
  }

  protected TypeParams targetParams(Vertex source, Edge edge) {
    TypeParams params = new TypeParams();
    params.addAll(source.params());
    params.addAll(ParamCollector.collect(edge.attr()));
    return params;
  }

  protected Vertex copy(Graph graph, Vertex vertex) {
    Vertex copy = new Vertex();
    if (graph.targets().contains(vertex)) {
      graph.targets().add(copy);
    }
    for (Edge e : vertex.edges()) {
      Edge c = new Edge();
      Vertex t = vertex == e.target() ? copy : e.target();
      c.target(t);
      c.attr(e.attr());
      copy.edges().add(c);
    }
    return copy;
  }
}
