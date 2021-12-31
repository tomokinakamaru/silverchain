package silverchain.graph.walker;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;
import org.apiguardian.api.API;
import silverchain.graph.data.Edge;
import silverchain.graph.data.Graph;
import silverchain.graph.data.Graphs;
import silverchain.graph.data.Vertex;

@API(status = API.Status.INTERNAL)
public interface GraphListener {

  default Comparator<Vertex> vertexComparator() {
    return Comparator.comparing(Objects::hashCode);
  }

  default Predicate<Vertex> vertexFilter() {
    return vertex -> true;
  }

  default Comparator<Edge> edgeComparator() {
    return Comparator.comparing(Objects::hashCode);
  }

  default Predicate<Edge> edgeFilter() {
    return edge -> true;
  }

  default void enter(Graphs graphs) {}

  default void enter(Graph graph) {}

  default void visit(Graph graph, Vertex vertex) {}

  default void visit(Graph graph, Vertex source, Edge edge) {}

  default void exit(Graph graph) {}

  default void exit(Graphs graphs) {}
}
