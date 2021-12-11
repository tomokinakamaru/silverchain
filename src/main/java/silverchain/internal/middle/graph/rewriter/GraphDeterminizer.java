package silverchain.internal.middle.graph.rewriter;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import silverchain.internal.middle.graph.ir.GraphVisitor;
import silverchain.internal.middle.graph.ir.attribute.Label;
import silverchain.internal.middle.graph.ir.graph.Edge;
import silverchain.internal.middle.graph.ir.graph.Graph;
import silverchain.internal.middle.graph.ir.graph.Node;
import silverchain.internal.middle.graph.ir.graph.collection.Nodes;

public class GraphDeterminizer extends GraphVisitor {

  @Override
  protected void enter(Graph graph) {
    Graph g = remove(graph);
    graph.sources(g.sources());
    graph.targets(g.targets());
  }

  static Graph remove(Graph graph) {
    Queue<Nodes> queue = new LinkedList<>();
    Set<Nodes> queued = new HashSet<>();
    Tracer<Nodes> tracer = new Tracer<>();

    Nodes root = EpsClosure.of(graph.sources());
    queue.add(root);
    queued.add(root);
    tracer.trace(root);

    while (!queue.isEmpty()) {
      Nodes sources = queue.remove();
      for (Label label : edgeLabels(sources)) {
        Nodes targets = EpsClosure.of(targets(sources, label));
        if (!queued.contains(targets)) queue.add(targets);
        tracer.trace(sources, targets, label);
      }
    }

    Graph g = new Graph();
    g.sources(tracer.find(nodes -> nodes == root));
    g.targets(tracer.find(nodes -> graph.targets().stream().anyMatch(nodes::contains)));
    return g;
  }

  private static Labels edgeLabels(Nodes nodes) {
    Labels labels = new Labels();
    nodes.forEach(node -> node.edges().forEach(labels::add));
    return labels;
  }

  private static Nodes targets(Nodes nodes, Label label) {
    return nodes.stream()
        .map(Node::edges)
        .flatMap(Collection::stream)
        .filter(e -> LabelComparator.equals(e.label(), label))
        .map(Edge::target)
        .collect(Collectors.toCollection(Nodes::new));
  }
}
