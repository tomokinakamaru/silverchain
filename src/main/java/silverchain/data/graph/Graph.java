package silverchain.data.graph;

import org.apiguardian.api.API;
import silverchain.data.graph.attribute.TypeDeclaration;

@API(status = API.Status.INTERNAL)
public class Graph {

  private Nodes sources = new Nodes();

  private Nodes targets = new Nodes();

  private TypeDeclaration typeDeclaration;

  public Nodes sources() {
    return sources;
  }

  public void sources(Nodes sources) {
    this.sources = sources;
  }

  public Nodes targets() {
    return targets;
  }

  public void targets(Nodes targets) {
    this.targets = targets;
  }

  public TypeDeclaration typeDeclaration() {
    return typeDeclaration;
  }

  public void typeDeclaration(TypeDeclaration typeDeclaration) {
    this.typeDeclaration = typeDeclaration;
  }
}
