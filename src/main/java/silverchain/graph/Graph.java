package silverchain.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Graph {

  GraphNodes startNodes;

  GraphNodes endNodes;

  GraphEdges edges;

  GraphCompileOption option;

  Graph() {}

  Graph(GraphNodes startNodes, GraphNodes endNodes, GraphEdges edges) {
    this.startNodes = startNodes;
    this.endNodes = endNodes;
    this.edges = edges;
  }

  public List<GraphNode> compile(GraphCompileOption option) {
    Graph graph = GraphBuilder.copy(this);
    graph.option = option;
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
        GraphTags tags = new GraphTags(src.tags);
        for (Object obj : option.getTags(edge.label)) {
          GraphTag tag = new GraphTag(obj);
          if (tags.stream().noneMatch(t -> option.equals(t, tag))) {
            tags.add(tag);
          }
        }

        if (dst.tags == null) {
          dst.tags = tags;
          traverser.enqueue(dst);
          continue;
        }
        if (dst.tags.equals(tags)) {
          continue;
        }
        GraphNode node = cloneDestination(edge);
        node.tags = tags;
        edges.remove(edge);
        edges.add(new GraphEdge(edge.source, node, edge.label));
        traverser.enqueue(node);
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
        .filter(e -> option.nullableEquals(e.label, label))
        .map(e -> e.destination)
        .collect(Collectors.toCollection(GraphNodes::new));
  }

  private List<GraphLabel> labelsFrom(GraphNodes nodes) {
    List<GraphLabel> labels = new ArrayList<>();
    for (GraphEdge edge : edges) {
      if (edge.label != null && nodes.contains(edge.source)) {
        if (labels.stream().noneMatch(l -> option.nullableEquals(l, edge.label))) {
          labels.add(edge.label);
        }
      }
    }
    return labels;
  }

  private List<GraphEdge> sortedEdges(GraphNode node) {
    return edges
        .from(node)
        .stream()
        .sorted((e1, e2) -> option.compareTo(e1.label, e2.label))
        .collect(Collectors.toCollection(ArrayList::new));
  }
}
