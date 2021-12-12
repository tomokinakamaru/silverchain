package silverchain.internal.middleware.graph.rewriter;

import silverchain.internal.middleware.graph.Tracer;
import silverchain.internal.middleware.graph.data.GraphVisitor;
import silverchain.internal.middleware.graph.data.graph.Edge;
import silverchain.internal.middleware.graph.data.graph.Graph;
import silverchain.internal.middleware.graph.data.graph.Node;
import silverchain.internal.middleware.graph.data.graph.collection.Nodes;

public class GraphReverser extends GraphVisitor {

  protected final Tracer<Node> tracer = new Tracer<>();

  @Override
  protected void visit(Node node) {
    tracer.trace(node);
  }

  @Override
  protected void visit(Edge edge) {
    tracer.trace(edge.target(), source(), edge.label());
  }

  @Override
  protected void exit(Graph graph) {
    Nodes sources = tracer.find(graph.targets()::contains);
    Nodes targets = tracer.find(graph.sources()::contains);
    graph.sources(sources);
    graph.targets(targets);
  }
}
