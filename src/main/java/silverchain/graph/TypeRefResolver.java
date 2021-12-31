package silverchain.graph;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.graph.data.Edge;
import silverchain.graph.data.Graph;
import silverchain.graph.data.Type;
import silverchain.graph.data.TypeParam;
import silverchain.graph.data.TypeRef;
import silverchain.graph.data.Vertex;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrWalker;
import silverchain.graph.walker.GraphListener;

@API(status = API.Status.INTERNAL)
public class TypeRefResolver implements AttrListener, GraphListener {

  protected Map<String, TypeParam> parameters;

  @Override
  public void enter(Graph graph) {
    AttrWalker.walk(this, graph.type());
  }

  @Override
  public void visit(Graph graph, Vertex source, Edge edge) {
    AttrWalker.walk(this, edge.attr());
  }

  @Override
  public void enter(Type type) {
    parameters = new HashMap<>();
    type.outerParams().forEach(p -> parameters.put(p.name(), p));
    type.innerParams().forEach(p -> parameters.put(p.name(), p));
  }

  @Override
  public void enter(TypeRef typeRef) {
    if (typeRef.name().qualifier() == null) {
      String id = typeRef.name().id();
      typeRef.target(parameters.get(id));
    }
  }
}
