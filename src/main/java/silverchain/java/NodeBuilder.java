package silverchain.java;

import com.github.javaparser.ast.body.MethodDeclaration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.graph.data.Edge;
import silverchain.graph.data.Graph;
import silverchain.graph.data.Graphs;
import silverchain.graph.data.Vertex;
import silverchain.graph.walker.GraphListener;
import silverchain.graph.walker.GraphWalker;
import silverchain.java.data.TypeNode;
import silverchain.java.data.TypesNode;

@API(status = API.Status.INTERNAL)
public final class NodeBuilder implements GraphListener {

  private final Map<Vertex, TypeNode> units = new HashMap<>();

  private NodeBuilder() {}

  public static TypesNode build(Graphs graphs) {
    NodeBuilder builder = new NodeBuilder();
    GraphWalker.walk(graphs, builder);
    return builder.units.values().stream().collect(Collectors.toCollection(TypesNode::new));
  }

  @Override
  public void enter(Graph graph) {
    units.putAll(NodeFactory.create(graph));
  }

  @Override
  public void visit(Graph graph, Vertex source, Edge edge) {}

  private static MethodDeclaration build(Vertex source, Edge edge) {
    return null;
  }
}
