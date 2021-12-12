package silverchain.internal.middleware.graph.builder;

import silverchain.internal.middleware.graph.Tracer;
import silverchain.internal.middleware.graph.data.GraphVisitor;
import silverchain.internal.middleware.graph.data.graph.Edge;
import silverchain.internal.middleware.graph.data.graph.Graph;
import silverchain.internal.middleware.graph.data.graph.Node;

public class GraphReplicator extends GraphVisitor {

  protected final Tracer<Node> tracer = new Tracer<>();

  public void replicate(Graph graph) {}

  @Override
  protected void visit(Node node) {
    super.visit(node);
  }

  @Override
  protected void visit(Edge edge) {
    super.visit(edge);
  }
}
