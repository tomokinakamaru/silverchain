package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class RangeNMTree extends TreeImpl implements RangeTree {

  private int min;

  private int max;

  public int min() {
    return min;
  }

  public void min(int min) {
    this.min = min;
  }

  public int max() {
    return max;
  }

  public void max(int max) {
    this.max = max;
  }

  @Override
  public RangeNMTree copy() {
    return (RangeNMTree) super.copy();
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
