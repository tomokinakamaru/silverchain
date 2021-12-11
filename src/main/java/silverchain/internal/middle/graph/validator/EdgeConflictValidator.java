package silverchain.internal.middle.graph.validator;

import java.util.ArrayList;
import java.util.List;
import silverchain.internal.middle.graph.ir.GraphVisitor;
import silverchain.internal.middle.graph.ir.attribute.Label;
import silverchain.internal.middle.graph.ir.attribute.Method;
import silverchain.internal.middle.graph.ir.graph.Edge;
import silverchain.internal.middle.graph.ir.graph.Node;

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
