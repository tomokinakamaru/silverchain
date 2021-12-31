package silverchain.java.data;

import java.util.HashSet;
import java.util.Set;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public final class NodeWalker {

  private NodeWalker() {}

  public static <T> void walk(Node node, NodeListener<T> listener, T arg) {
    walk(node, listener, arg, new HashSet<>());
  }

  private static <T> void walk(Node node, NodeListener<T> listener, T arg, Set<Node> walked) {
    if (!walked.contains(node)) {
      walked.add(node);
      node.enter(listener, arg);
      node.children().forEach(n -> walk(n, listener, arg, walked));
      node.exit(listener, arg);
    }
  }
}
