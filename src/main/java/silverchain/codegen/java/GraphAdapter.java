package silverchain.codegen.java;

import static silverchain.codegen.java.ASTEncoder.encode;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import silverchain.grammar.Type;
import silverchain.graph.GraphNode;

final class GraphAdapter {

  private final List<GraphNode> nodes;

  private final Type type;

  private final Set<String> parameters;

  GraphAdapter(List<GraphNode> nodes) {
    this.nodes = nodes;
    this.type = nodes.get(0).edges().get(0).label().as(Type.class);
    this.parameters = nodes.stream().flatMap(n -> n.tags().stream()).collect(Collectors.toSet());
  }

  List<GraphNodeAdapter> nodes() {
    return nodes
        .stream()
        .filter(n -> !n.isStart() && !n.isEnd())
        .map(n -> new GraphNodeAdapter(this, n))
        .collect(Collectors.toList());
  }

  String typeName() {
    return type.name().name();
  }

  String typePackageName() {
    return type.name().qualifier() == null ? null : encode(type.name().qualifier());
  }

  int indexOf(GraphNode node) {
    return nodes
        .stream()
        .filter(n -> !n.isStart() && !n.isEnd())
        .collect(Collectors.toList())
        .indexOf(node);
  }

  boolean isParameter(String name) {
    return parameters.contains(name);
  }
}
