package silverchain.process.graph.rewriter;

import java.util.function.Predicate;
import silverchain.data.graph.Edge;
import silverchain.data.graph.Graph;
import silverchain.data.graph.Graphs;
import silverchain.data.graph.Node;
import silverchain.data.graph.Nodes;
import silverchain.data.graph.visitor.GraphListener;
import silverchain.data.graph.visitor.GraphWalker;

class EpsClosure implements GraphListener {

  private final Nodes nodes = new Nodes();

  private EpsClosure() {}

  static Nodes of(Nodes nodes) {
    EpsClosure closure = new EpsClosure();
    Graph graph = new Graph();
    graph.sources(nodes);
    Graphs graphs = new Graphs();
    graphs.add(graph);
    new GraphWalker().walk(closure, graphs);
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
