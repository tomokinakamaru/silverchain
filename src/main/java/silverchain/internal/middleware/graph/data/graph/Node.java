package silverchain.internal.middleware.graph.data.graph;

import org.apiguardian.api.API;
import silverchain.internal.middleware.graph.data.attribute.collection.TypeParameters;
import silverchain.internal.middleware.graph.data.graph.collection.Edges;

@API(status = API.Status.INTERNAL)
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
