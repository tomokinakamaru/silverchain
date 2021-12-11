package silverchain.internal.middle.graph.ir;

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;
import silverchain.internal.middle.graph.ir.graph.Edge;
import silverchain.internal.middle.graph.ir.graph.Graph;
import silverchain.internal.middle.graph.ir.graph.Node;
import silverchain.internal.middle.graph.ir.graph.collection.Graphs;
import silverchain.internal.middle.graph.ir.graph.collection.Nodes;

public abstract class GraphVisitor {

  private final Comparator<Node> nodeComparator = nodeComparator();

  private final Comparator<Edge> edgeComparator = edgeComparator();

  private final Predicate<Edge> edgeFilter = edgeFilter();

  private Queue<Entry<Node, Edge>> queue;

  private Set<Node> visited;

  private Graph graph;

  private Node source;

  protected Comparator<Node> nodeComparator() {
    return Comparator.comparing(Objects::hashCode);
  }

  protected Comparator<Edge> edgeComparator() {
    return Comparator.comparing(Objects::hashCode);
  }

  protected Predicate<Edge> edgeFilter() {
    return edge -> true;
  }

  public final void visit(Graphs graphs) {}

  public final void visit(Graph graph) {
    this.graph = graph;
    queue = new LinkedList<>();
    visited = new HashSet<>();
    enter(graph);
    visitIfNotVisited(graph.sources());
    while (!queue.isEmpty()) {
      Entry<Node, Edge> entry = queue.remove();
      Edge edge = entry.getValue();
      Node source = entry.getKey();
      Node target = edge.target();
      this.source = source;
      visit(edge);
      this.source = null;
      visitIfNotVisited(target);
    }
    exit(graph);
  }

  protected void enter(Graphs graphs) {}

  protected void enter(Graph graph) {}

  protected void visit(Node node) {}

  protected void visit(Edge edge) {}

  protected void exit(Graph graph) {}

  protected void exit(Graphs graphs) {}

  protected final Graph graph() {
    return graph;
  }

  protected final Node source() {
    return source;
  }

  private void visitIfNotVisited(Nodes nodes) {
    nodes.stream().sorted(nodeComparator).forEach(this::visitIfNotVisited);
  }

  private void visitIfNotVisited(Node node) {
    if (!visited.contains(node)) {
      visit(node);
      visited.add(node);
      enqueueEdges(node);
    }
  }

  private void enqueueEdges(Node node) {
    node.edges().stream()
        .filter(edgeFilter)
        .sorted(edgeComparator)
        .map(e -> new SimpleEntry<>(node, e))
        .forEach(queue::add);
  }
}
