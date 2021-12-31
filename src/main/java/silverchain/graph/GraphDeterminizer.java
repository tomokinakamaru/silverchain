package silverchain.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.graph.data.Edge;
import silverchain.graph.data.EdgeAttr;
import silverchain.graph.data.Graph;
import silverchain.graph.data.Vertex;
import silverchain.graph.data.Vertices;
import silverchain.graph.walker.GraphListener;

@API(status = API.Status.INTERNAL)
public class GraphDeterminizer implements GraphListener {

  @Override
  public void enter(Graph graph) {
    Graph g = determinize(graph);
    graph.sources(g.sources());
    graph.targets(g.targets());
  }

  protected Graph determinize(Graph graph) {
    Queue<Vertices> queue = new LinkedList<>();
    Set<Vertices> queued = new HashSet<>();
    Tracer<Vertices> tracer = new Tracer<>();

    Vertices root = EpsClosureFinder.find(graph.sources());
    queue.add(root);
    queued.add(root);
    tracer.trace(root);

    while (!queue.isEmpty()) {
      Vertices sources = queue.remove();
      for (EdgeAttr attribute : AttrCollector.collect(sources)) {
        Vertices targets = EpsClosureFinder.find(targets(sources, attribute));
        if (!queued.contains(targets)) {
          queue.add(targets);
          queued.add(targets);
        }
        tracer.trace(sources, targets, attribute);
      }
    }

    Graph g = new Graph();
    g.sources(tracer.find(vertices -> vertices == root));
    g.targets(tracer.find(vertices -> graph.targets().stream().anyMatch(vertices::contains)));
    return g;
  }

  protected Vertices targets(Vertices vertices, EdgeAttr attribute) {
    return vertices.stream()
        .map(Vertex::edges)
        .flatMap(Collection::stream)
        .filter(e -> AttrComparator.equals(e.attr(), attribute))
        .map(Edge::target)
        .collect(Collectors.toCollection(Vertices::new));
  }
}
