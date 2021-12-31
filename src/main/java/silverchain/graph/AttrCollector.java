package silverchain.graph;

import java.util.LinkedHashSet;
import java.util.Set;
import org.apiguardian.api.API;
import silverchain.graph.data.Edge;
import silverchain.graph.data.EdgeAttr;
import silverchain.graph.data.Edges;
import silverchain.graph.data.LocationGroup;
import silverchain.graph.data.LocationGroups;
import silverchain.graph.data.Vertex;
import silverchain.graph.data.Vertices;

@API(status = API.Status.INTERNAL)
public final class AttrCollector {

  private final Set<EdgeAttr> attrs = new LinkedHashSet<>();

  private AttrCollector() {}

  public static Set<EdgeAttr> collect(Vertices vertices) {
    AttrCollector collector = new AttrCollector();
    vertices.forEach(vertex -> collector.collect(vertex.edges()));
    return collector.attrs;
  }

  public static Set<EdgeAttr> collect(Vertex vertex) {
    AttrCollector collector = new AttrCollector();
    collector.collect(vertex.edges());
    return collector.attrs;
  }

  private void collect(Edges edges) {
    edges.forEach(this::collect);
  }

  private void collect(Edge edge) {
    EdgeAttr attribute = edge.attr();
    if (attribute != null) add(attribute);
  }

  private void add(EdgeAttr attr) {
    EdgeAttr a = find(attr);
    if (a == null) {
      attrs.add(attr);
    } else {
      if (a.isMethod()) {
        LocationGroups ls = attr.asMethod().locations();
        a.asMethod().locations().addAll(ls);
      } else {
        LocationGroup ls = attr.asRetType().locations();
        a.asRetType().locations().addAll(ls);
      }
    }
  }

  private EdgeAttr find(EdgeAttr attr) {
    return attrs.stream().filter(a -> AttrComparator.equals(a, attr)).findFirst().orElse(null);
  }
}
