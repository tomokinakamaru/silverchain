package silverchain.internal.middle.data.graph;

import silverchain.internal.middle.data.attribute.TypeDeclaration;
import silverchain.internal.middle.data.graph.collection.Nodes;

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
