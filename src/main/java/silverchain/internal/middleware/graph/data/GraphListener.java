package silverchain.internal.middleware.graph.data;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;
import org.apiguardian.api.API;
import silverchain.internal.middleware.graph.data.graph.Edge;
import silverchain.internal.middleware.graph.data.graph.Graph;
import silverchain.internal.middleware.graph.data.graph.Node;
import silverchain.internal.middleware.graph.data.graph.collection.Graphs;

@API(status = API.Status.INTERNAL)
public interface GraphListener {

  default Comparator<Node> nodeComparator() {
    return Comparator.comparing(Objects::hashCode);
  }

  default Comparator<Edge> edgeComparator() {
    return Comparator.comparing(Objects::hashCode);
  }

  default Predicate<Edge> edgeFilter() {
    return edge -> true;
  }

  default void enter(Graphs graphs) {}

  default void enter(Graph graph) {}

  default void visit(Graph graph, Node node) {}

  default void visit(Graph graph, Node source, Edge edge) {}

  default void exit(Graph graph) {}

  default void exit(Graphs graphs) {}
}
