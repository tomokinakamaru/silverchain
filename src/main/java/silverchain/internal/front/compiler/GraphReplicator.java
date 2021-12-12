package silverchain.internal.front.compiler;

import org.graalvm.compiler.core.common.alloc.Trace;
import silverchain.internal.middle.data.GraphVisitor;
import silverchain.internal.middle.data.graph.Edge;
import silverchain.internal.middle.data.graph.Graph;
import silverchain.internal.middle.data.graph.Node;
import silverchain.internal.utility.Tracer;

public class GraphReplicator extends GraphVisitor {

  protected final Tracer<Node> tracer = new Tracer<>();

  public void replicate(Graph graph) {
  }

  @Override
  protected void visit(Node node) {
    super.visit(node);
  }

  @Override
  protected void visit(Edge edge) {
    super.visit(edge);
  }
}
