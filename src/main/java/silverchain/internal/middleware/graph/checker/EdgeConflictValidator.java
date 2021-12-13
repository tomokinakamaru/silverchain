package silverchain.internal.middleware.graph.checker;

import java.util.ArrayList;
import java.util.List;
import silverchain.internal.middleware.graph.data.GraphListener;
import silverchain.internal.middleware.graph.data.attribute.Label;
import silverchain.internal.middleware.graph.data.attribute.Method;
import silverchain.internal.middleware.graph.data.graph.Edge;
import silverchain.internal.middleware.graph.data.graph.Graph;
import silverchain.internal.middleware.graph.data.graph.Node;

public class EdgeConflictValidator implements GraphListener {

  @Override
  public void visit(Graph graph, Node node) {
    int typeCount = 0;
    int methodCount = 0;
    List<Label> labels = new ArrayList<>();
    for (Edge edge : node.edges()) {
      Label label = edge.label();
      labels.add(label);
      if (label instanceof Method) {
        methodCount++;
      } else {
        typeCount++;
      }
    }
    if (1 < typeCount || (0 < typeCount && 0 < methodCount)) {
      throw new EdgeConflict(labels);
    }
  }
}
