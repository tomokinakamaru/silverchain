package silverchain.ag.data;

import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class ChainTermTree extends Tree<ChainTermTree> implements SetTree<ChainFactTree> {

  @Override
  public <T> void enter(TreeListener<T> listener, T arg) {
    listener.enter(this, arg);
  }

  @Override
  public <T> void exit(TreeListener<T> listener, T arg) {
    listener.exit(this, arg);
  }

  @Override
  public String toString() {
    return stream().map(ChainFactTree::toString).collect(Collectors.joining(" "));
  }
}