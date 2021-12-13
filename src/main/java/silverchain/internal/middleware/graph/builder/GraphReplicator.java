package silverchain.internal.middleware.graph.builder;

import silverchain.internal.middleware.graph.Tracer;
import silverchain.internal.middleware.graph.data.GraphListener;
import silverchain.internal.middleware.graph.data.graph.Edge;
import silverchain.internal.middleware.graph.data.graph.Graph;
import silverchain.internal.middleware.graph.data.graph.Node;

public class GraphReplicator implements GraphListener {

  protected final Tracer<Node> tracer = new Tracer<>();

  public void replicate(Graph graph) {}

  @Override
  public void visit(Graph graph, Node node) {}

  @Override
  public void visit(Graph graph, Node source, Edge edge) {}
}
