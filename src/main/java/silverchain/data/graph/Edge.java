package silverchain.data.graph;

import org.apiguardian.api.API;
import silverchain.data.graph.attribute.Label;

@API(status = API.Status.INTERNAL)
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
