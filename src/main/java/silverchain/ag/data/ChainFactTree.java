package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class ChainFactTree extends TreeImpl {

  public ChainElemTree elem() {
    return children().find(ChainElemTree.class);
  }

  public QuantifierTree quantifier() {
    return children().find(QuantifierTree.class);
  }

  @Override
  public ChainFactTree copy() {
    return (ChainFactTree) super.copy();
  }

  @Override
  public <T> void enter(TreeStack ancestors, TreeListener<T> listener, T arg) {
    listener.enter(ancestors, this, arg);
  }

  @Override
  public <T> void exit(TreeStack ancestors, TreeListener<T> listener, T arg) {
    listener.exit(ancestors, this, arg);
  }
}
