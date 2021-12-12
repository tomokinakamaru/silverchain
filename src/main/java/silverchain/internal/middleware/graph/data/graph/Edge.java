package silverchain.internal.middleware.graph.data.graph;

import silverchain.internal.middleware.graph.data.attribute.Label;

public class Edge {

  private Node target;

  private Label label;

  public Node target() {
    return target;
  }

  public void target(Node target) {
    this.target = target;
  }

  public Label label() {
    return label;
  }

  public void label(Label label) {
    this.label = label;
  }
}
