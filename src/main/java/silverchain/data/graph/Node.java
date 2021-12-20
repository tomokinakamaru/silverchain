package silverchain.data.graph;

import org.apiguardian.api.API;
import silverchain.data.graph.attribute.collection.TypeParameters;

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
