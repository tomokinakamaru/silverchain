package silverchain.internal.middle.graph.parser;

import silverchain.internal.middle.graph.Tracer;
import silverchain.internal.middle.graph.data.GraphVisitor;
import silverchain.internal.middle.graph.data.graph.Edge;
import silverchain.internal.middle.graph.data.graph.Graph;
import silverchain.internal.middle.graph.data.graph.Node;

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
