package silverchain.process.graph.rewriter;

import silverchain.data.graph.Edge;
import silverchain.data.graph.Edges;
import silverchain.data.graph.Graph;
import silverchain.data.graph.Node;
import silverchain.data.graph.attribute.collection.TypeParameters;
import silverchain.data.graph.visitor.GraphListener;

public class ParamPropagator implements GraphListener {

  @Override
  public void enter(Graph graph) {
    graph.sources().forEach(node -> node.parameters(new TypeParameters()));
  }

  @Override
  public void visit(Graph graph, Node node) {
    Edges edges = new Edges();
    edges.addAll(node.edges());
    for (Edge edge : edges) propagate(graph, node, edge);
  }

  private void propagate(Graph graph, Node source, Edge edge) {
    Node target = edge.target();
    TypeParameters params = targetParams(source, edge);
    if (target.parameters() == null) {
      target.parameters(params);
    } else if (!target.parameters().equals(params)) {
      Node copy = copy(graph, edge.target());
      copy.parameters(params);
      Edge e = new Edge();
      e.target(copy);
      e.label(edge.label());
      source.edges().remove(edge);
      source.edges().add(e);
    }
  }

  private TypeParameters targetParams(Node source, Edge edge) {
    TypeParameters params = new TypeParameters();
    params.addAll(source.parameters());
    params.addAll(edge.label().accept(new ParamRefCollector(), new TypeParameters()));
    return params;
  }

  private Node copy(Graph graph, Node node) {
    Node copy = new Node();
    if (graph.targets().contains(node)) {
      graph.targets().add(copy);
    }
    for (Edge e : node.edges()) {
      Edge c = new Edge();
      Node t = node == e.target() ? copy : e.target();
      c.target(t);
      c.label(e.label());
      copy.edges().add(c);
    }
    return copy;
  }
}
