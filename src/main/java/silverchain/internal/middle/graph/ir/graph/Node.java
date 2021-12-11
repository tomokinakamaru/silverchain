package silverchain.internal.middle.graph.ir.graph;

import silverchain.internal.middle.graph.ir.attribute.collection.TypeParameters;
import silverchain.internal.middle.graph.ir.graph.collection.Edges;

public class Node {

  private Edges edges = new Edges();

  private TypeParameters parameters;

  public Edges edges() {
    return edges;
  }

  public void edges(Edges edges) {
    this.edges = edges;
  }

  public TypeParameters parameters() {
    return parameters;
  }

  public void parameters(TypeParameters parameters) {
    this.parameters = parameters;
  }
}
