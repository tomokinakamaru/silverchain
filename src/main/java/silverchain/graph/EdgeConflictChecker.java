package silverchain.graph;

import java.util.Set;
import org.apiguardian.api.API;
import silverchain.graph.data.EdgeAttr;
import silverchain.graph.data.Graph;
import silverchain.graph.data.Vertex;
import silverchain.graph.error.EdgeConflict;
import silverchain.graph.walker.GraphListener;

@API(status = API.Status.INTERNAL)
public class EdgeConflictChecker implements GraphListener {

  @Override
  public void visit(Graph graph, Vertex vertex) {
    Set<EdgeAttr> attrs = AttrCollector.collect(vertex);
    long typeCount = attrs.stream().filter(EdgeAttr::isTypeRef).count();
    long methodCount = attrs.stream().filter(EdgeAttr::isMethod).count();
    if (1 < typeCount || (0 < typeCount && 0 < methodCount)) {
      throw new EdgeConflict(attrs);
    }
  }
}
