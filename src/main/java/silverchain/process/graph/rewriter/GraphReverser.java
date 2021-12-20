package silverchain.process.graph.rewriter;

import silverchain.data.graph.Edge;
import silverchain.data.graph.Graph;
import silverchain.data.graph.Node;
import silverchain.data.graph.Nodes;
import silverchain.data.graph.visitor.GraphListener;

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
