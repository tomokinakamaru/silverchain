package silverchain.graph.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class Edge {

  private Vertex target;

  private EdgeAttr attr;

  public Vertex target() {
    return target;
  }

  public void target(Vertex target) {
    this.target = target;
  }

  public EdgeAttr attr() {
    return attr;
  }

  public void attr(EdgeAttr attr) {
    this.attr = attr;
  }
}
