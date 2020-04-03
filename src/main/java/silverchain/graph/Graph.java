package silverchain.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import silverchain.parser.Method;
import silverchain.parser.Type;
import silverchain.parser.TypeParameter;
import silverchain.parser.TypeParameters;

public final class Graph {

  GraphNodes startNodes;

  GraphNodes endNodes;

  GraphEdges edges;

  Graph() {}

  Graph(GraphNodes startNodes, GraphNodes endNodes, GraphEdges edges) {
    this.startNodes = startNodes;
    this.endNodes = endNodes;
    this.edges = edges;
  }

  public List<GraphNode> compile() {
    Graph graph = GraphBuilders.copy(this);
    graph.reverse();
    graph.determinize();
    graph.reverse();
    graph.determinize();
    graph.propagateTags();

    Traverser<GraphNode> traverser = new Traverser<>(graph.startNodes);
    while (traverser.hasNext()) {
      GraphNode node = traverser.next();
      node.sortedEdges = graph.sortedEdges(node);
      node.isStart = graph.startNodes.contains(node);
      node.isEnd = graph.endNodes.contains(node);
      node.sortedEdges.forEach(e -> traverser.enqueue(e.destination));
    }
    return traverser.queued();
  }

  private void reverse() {
    GraphNodes nodes = startNodes;
    startNodes = endNodes;
    endNodes = nodes;
    edges.reverse();
  }

  private void determinize() {
    GraphNodes initial = epsilonClosure(startNodes);
    Tracer<GraphNodes> tracer = new Tracer<GraphNodes>(initial);
    Traverser<GraphNodes> traverser = new Traverser<GraphNodes>(initial);
    while (traverser.hasNext()) {
      GraphNodes src = traverser.next();
      for (GraphLabel label : labelsFrom(src)) {
        GraphNodes dst = epsilonClosure(destinations(src, label));
        traverser.enqueue(dst);
        tracer.trace(src, dst, label);
      }
    }
    startNodes = tracer.nodes(nodes -> nodes == initial);
    endNodes = tracer.nodes(nodes -> endNodes.stream().anyMatch(nodes::contains));
    edges = tracer.edges();
  }

  private void propagateTags() {
    GraphNode initial = startNodes.iterator().next();
    initial.tags = new GraphTags();

    Traverser<GraphNode> traverser = new Traverser<>(initial);
    while (traverser.hasNext()) {
      GraphNode src = traverser.next();
      for (GraphEdge edge : edges.from(src)) {
        GraphNode dst = edge.destination;
        GraphTags tags = new GraphTags(src.tags, getTags(edge.label));

        if (dst.tags == null) {
          dst.tags = tags;
          traverser.enqueue(dst);
        } else if (!dst.tags.equals(tags)) {
          GraphNode node = cloneDestination(edge);
          node.tags = tags;
          edges.remove(edge);
          edges.add(new GraphEdge(edge.source, node, edge.label));
          traverser.enqueue(node);
        }
      }
    }
  }

  private GraphNode cloneDestination(GraphEdge edge) {
    GraphNode original = edge.destination;
    GraphNode cloned = new GraphNode();
    if (endNodes.contains(original)) {
      endNodes.add(cloned);
    }
    for (GraphEdge e : edges.from(original)) {
      GraphNode d = e.source == e.destination ? cloned : e.destination;
      edges.add(new GraphEdge(cloned, d, e.label));
    }
    return cloned;
  }

  private GraphNodes epsilonClosure(GraphNodes nodes) {
    Traverser<GraphNode> traverser = new Traverser<>(nodes);
    while (traverser.hasNext()) {
      GraphNode node = traverser.next();
      traverser.enqueue(destinations(new GraphNodes(node), null));
    }
    return traverser.queued().stream().collect(Collectors.toCollection(GraphNodes::new));
  }

  private GraphNodes destinations(GraphNodes sources, GraphLabel label) {
    return edges
        .stream()
        .filter(e -> sources.contains(e.source))
        .filter(e -> Objects.equals(e.label, label))
        .map(e -> e.destination)
        .collect(Collectors.toCollection(GraphNodes::new));
  }

  private GraphLabels labelsFrom(GraphNodes nodes) {
    GraphLabels labels = new GraphLabels();
    edges.stream().filter(e -> nodes.contains(e.source)).forEach(e -> labels.add(e.label));
    return labels;
  }

  private List<GraphEdge> sortedEdges(GraphNode node) {
    return edges.from(node).stream().sorted().collect(Collectors.toCollection(ArrayList::new));
  }

  private Iterable<TypeParameter> getTags(GraphLabel label) {
    if (label.is(Method.class)) {
      return new ArrayList<>(label.as(Method.class).referents());
    }
    if (label.is(Type.class)) {
      Optional<TypeParameters> parameters = label.as(Type.class).parameters();
      if (parameters.isPresent() && parameters.get().publicList().isPresent()) {
        return parameters.get().publicList().get();
      }
    }
    return Collections.emptyList();
  }
}
