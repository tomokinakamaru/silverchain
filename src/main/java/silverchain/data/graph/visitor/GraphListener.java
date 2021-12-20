package silverchain.data.graph.visitor;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;
import org.apiguardian.api.API;
import silverchain.data.graph.Edge;
import silverchain.data.graph.Graph;
import silverchain.data.graph.Graphs;
import silverchain.data.graph.Node;

@API(status = API.Status.INTERNAL)
public interface GraphListener {

  default Comparator<Node> nodeComparator() {
    return Comparator.comparing(Objects::hashCode);
  }

  default Predicate<Node> nodeFilter() {
    return node -> true;
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
