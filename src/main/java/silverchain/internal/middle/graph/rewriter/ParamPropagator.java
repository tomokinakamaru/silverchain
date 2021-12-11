package silverchain.internal.middle.graph.rewriter;

import silverchain.internal.middle.graph.ir.GraphVisitor;
import silverchain.internal.middle.graph.ir.attribute.collection.TypeParameters;
import silverchain.internal.middle.graph.ir.graph.Edge;
import silverchain.internal.middle.graph.ir.graph.Graph;
import silverchain.internal.middle.graph.ir.graph.Node;
import silverchain.internal.middle.graph.ir.graph.collection.Edges;

public class ParamPropagator extends GraphVisitor {

  @Override
  protected void enter(Graph graph) {
    graph.sources().forEach(node -> node.parameters(new TypeParameters()));
  }

  @Override
  protected void visit(Node node) {
    Edges edges = new Edges();
    edges.addAll(node.edges());
    for (Edge edge : edges) visit(node, edge);
  }

  private void visit(Node source, Edge edge) {
    Node target = edge.target();
    TypeParameters params = targetParams(source, edge);
    if (target.parameters() == null) {
      target.parameters(params);
    } else if (!target.parameters().equals(params)) {
      Node copy = copy(edge.target());
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

  private Node copy(Node node) {
    Node copy = new Node();
    if (graph().targets().contains(node)) {
      graph().targets().add(copy);
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
