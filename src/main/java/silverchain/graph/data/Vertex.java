package silverchain.graph.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class Vertex {

  private Edges edges = new Edges();

  private TypeParams params;

  public Edges edges() {
    return edges;
  }

  public void edges(Edges edges) {
    this.edges = edges;
  }

  public TypeParams params() {
    return params;
  }

  public void params(TypeParams params) {
    this.params = params;
  }
}
