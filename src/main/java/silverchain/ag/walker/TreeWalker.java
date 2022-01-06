package silverchain.ag.walker;

import org.apiguardian.api.API;
import silverchain.ag.data.Tree;

@API(status = API.Status.INTERNAL)
public class TreeWalker {

  public static void walk(Tree tree, TreeListener<Void> listener) {
    walk(tree, listener, null);
  }

  public static <T> void walk(Tree tree, TreeListener<T> listener, T arg) {
    walk(tree, listener, arg, new TreeStack());
  }

  protected static <T> void walk(Tree tree, TreeListener<T> listener, T arg, TreeStack stack) {
    tree.enter(stack, listener, arg);
    stack.push(tree);
    tree.children().forEach(t -> walk(t, listener, arg, stack));
    stack.pop();
    tree.exit(stack, listener, arg);
  }
}
