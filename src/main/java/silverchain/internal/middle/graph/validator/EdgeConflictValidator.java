package silverchain.internal.middle.graph.validator;

import java.util.ArrayList;
import java.util.List;
import silverchain.internal.middle.data.GraphVisitor;
import silverchain.internal.middle.data.attribute.Label;
import silverchain.internal.middle.data.attribute.Method;
import silverchain.internal.middle.data.graph.Edge;
import silverchain.internal.middle.data.graph.Node;

public class EdgeConflictValidator extends GraphVisitor {

  @Override
  protected void visit(Node node) {
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
