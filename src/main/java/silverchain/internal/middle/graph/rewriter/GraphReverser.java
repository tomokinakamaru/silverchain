package silverchain.internal.middle.graph.rewriter;

import silverchain.internal.middle.graph.ir.GraphVisitor;
import silverchain.internal.middle.graph.ir.graph.Edge;
import silverchain.internal.middle.graph.ir.graph.Graph;
import silverchain.internal.middle.graph.ir.graph.Node;
import silverchain.internal.middle.graph.ir.graph.collection.Nodes;

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
