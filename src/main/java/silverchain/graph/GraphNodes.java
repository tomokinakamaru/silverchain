package silverchain.graph;

import java.util.HashSet;

final class GraphNodes extends HashSet<GraphNode> {

  GraphNodes() {}

  GraphNodes(GraphNode node) {
    add(node);
  }

  GraphNodes(GraphNodes nodes1, GraphNodes nodes2) {
    addAll(nodes1);
    addAll(nodes2);
  }
}
