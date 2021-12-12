package silverchain.internal.middleware.graph.data.graph;

import silverchain.internal.middleware.graph.data.attribute.collection.TypeParameters;
import silverchain.internal.middleware.graph.data.graph.collection.Edges;

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