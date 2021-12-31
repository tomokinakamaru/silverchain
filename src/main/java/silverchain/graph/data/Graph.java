package silverchain.graph.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class Graph {

  private Vertices sources = new Vertices();

  private Vertices targets = new Vertices();

  private Type type;

  public Vertices sources() {
    return sources;
  }

  public void sources(Vertices sources) {
    this.sources = sources;
  }

  public Vertices targets() {
    return targets;
  }

  public void targets(Vertices targets) {
    this.targets = targets;
  }

  public Type type() {
    return type;
  }

  public void type(Type type) {
    this.type = type;
  }
}
