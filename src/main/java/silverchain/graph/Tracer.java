package silverchain.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.graph.data.Edge;
import silverchain.graph.data.EdgeAttr;
import silverchain.graph.data.Vertex;
import silverchain.graph.data.Vertices;

@API(status = API.Status.INTERNAL)
public final class Tracer<T> {

  private final Map<T, Vertex> map = new HashMap<>();

  public void trace(T object) {
    map.computeIfAbsent(object, o -> new Vertex());
  }

  public void trace(T source, T target, EdgeAttr attr) {
    trace(source);
    trace(target);
    Edge edge = new Edge();
    edge.attr(attr);
    edge.target(map.get(target));
    map.get(source).edges().add(edge);
  }

  public Vertices find(Predicate<T> predicate) {
    return map.entrySet().stream()
        .filter(entry -> predicate.test(entry.getKey()))
        .map(Map.Entry::getValue)
        .collect(Collectors.toCollection(Vertices::new));
  }
}
