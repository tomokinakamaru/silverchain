package silverchain.java;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.graph.data.Graph;
import silverchain.graph.data.Type;
import silverchain.graph.data.Vertex;
import silverchain.graph.walker.GraphListener;
import silverchain.graph.walker.GraphWalker;
import silverchain.java.data.ClassNode;
import silverchain.java.data.TypeNode;

@API(status = API.Status.INTERNAL)
public class NodeFactory implements GraphListener {

  private final Map<Vertex, TypeNode> map = new HashMap<>();

  private int nextNumber = 0;

  public static Map<Vertex, TypeNode> create(Graph graph) {
    NodeFactory factory = new NodeFactory();
    GraphWalker.walk(graph, factory);
    return factory.map;
  }

  @Override
  public void visit(Graph graph, Vertex vertex) {
    create(vertex, graph.type());
  }

  private void create(Vertex vertex, Type type) {
    if (vertex.edges().stream().anyMatch(e -> e.attr().isMethod())) {
      String pkgName = packageName(type);
      String typeName = type.name().id() + nextNumber;
      create(vertex, pkgName, typeName);
      nextNumber++;
    }
  }

  private void create(Vertex vertex, String pkgName, String typeName) {
    ClassNode node = new ClassNode();
    node.packageName(pkgName);
    map.put(vertex, node);
  }

  private String packageName(Type type) {
    if (type.name().qualifier() == null) {
      return null;
    } else {
      return String.join(",", type.name().qualifier());
    }
  }
}
