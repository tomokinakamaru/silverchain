package silverchain.internal.middle.graph.rewriter;

import java.util.function.Predicate;
import silverchain.internal.middle.graph.ir.GraphVisitor;
import silverchain.internal.middle.graph.ir.graph.Edge;
import silverchain.internal.middle.graph.ir.graph.Graph;
import silverchain.internal.middle.graph.ir.graph.Node;
import silverchain.internal.middle.graph.ir.graph.collection.Nodes;

class EpsClosure extends GraphVisitor {

  private final Nodes nodes = new Nodes();

  private EpsClosure() {}

  static Nodes of(Nodes nodes) {
    EpsClosure closure = new EpsClosure();
    Graph graph = new Graph();
    graph.sources(nodes);
    closure.visit(graph);
    return closure.nodes;
  }

  @Override
  protected void visit(Node node) {
    nodes.add(node);
  }

  @Override
  protected Predicate<Edge> edgeFilter() {
    return edge -> edge.label() == null;
  }
}
