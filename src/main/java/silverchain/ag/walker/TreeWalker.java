package silverchain.ag.walker;

import org.apiguardian.api.API;
import silverchain.ag.data.Tree;

@API(status = API.Status.INTERNAL)
public class TreeWalker {

  public static void walk(Tree<?> tree, TreeListener<Void> listener) {
    walk(tree, listener, null);
  }

  public static <T> void walk(Tree<?> tree, TreeListener<T> listener, T arg) {
    tree.enter(listener, arg);
    tree.children().forEach(t -> walk(t, listener, arg));
    tree.exit(listener, arg);
  }
}
