package silverchain.process.graph.checker;

import java.util.ArrayList;
import java.util.List;
import org.apiguardian.api.API;
import silverchain.data.graph.Edge;
import silverchain.data.graph.Graph;
import silverchain.data.graph.Node;
import silverchain.data.graph.attribute.Label;
import silverchain.data.graph.attribute.Method;
import silverchain.data.graph.visitor.GraphListener;

@API(status = API.Status.INTERNAL)
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
