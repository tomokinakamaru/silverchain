package silverchain.internal.middleware.graph.rewriter;

import java.util.function.Predicate;
import silverchain.internal.middleware.graph.data.GraphListener;
import silverchain.internal.middleware.graph.data.GraphWalker;
import silverchain.internal.middleware.graph.data.graph.Edge;
import silverchain.internal.middleware.graph.data.graph.Graph;
import silverchain.internal.middleware.graph.data.graph.Node;
import silverchain.internal.middleware.graph.data.graph.collection.Nodes;

class EpsClosure implements GraphListener {

  private final Nodes nodes = new Nodes();

  private EpsClosure() {}

  static Nodes of(Nodes nodes) {
    EpsClosure closure = new EpsClosure();
    Graph graph = new Graph();
    graph.sources(nodes);
    new GraphWalker().walk(closure, graph);
    return closure.nodes;
  }

  @Override
  public void visit(Graph graph, Node node) {
    nodes.add(node);
  }

  @Override
  public Predicate<Edge> edgeFilter() {
    return edge -> edge.label() == null;
  }
}
