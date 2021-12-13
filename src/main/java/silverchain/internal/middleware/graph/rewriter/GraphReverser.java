package silverchain.internal.middleware.graph.rewriter;

import silverchain.internal.middleware.graph.Tracer;
import silverchain.internal.middleware.graph.data.GraphListener;
import silverchain.internal.middleware.graph.data.graph.Edge;
import silverchain.internal.middleware.graph.data.graph.Graph;
import silverchain.internal.middleware.graph.data.graph.Node;
import silverchain.internal.middleware.graph.data.graph.collection.Nodes;

public class GraphReverser implements GraphListener {

  protected final Tracer<Node> tracer = new Tracer<>();

  @Override
  public void visit(Graph graph, Node node) {
    tracer.trace(node);
  }

  @Override
  public void visit(Graph graph, Node source, Edge edge) {
    tracer.trace(edge.target(), source, edge.label());
  }

  @Override
  public void exit(Graph graph) {
    Nodes sources = tracer.find(graph.targets()::contains);
    Nodes targets = tracer.find(graph.sources()::contains);
    graph.sources(sources);
    graph.targets(targets);
  }
}
