package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class RangeNTree extends TreeImpl implements RangeTree {

  private int n;

  public int n() {
    return n;
  }

  public void n(int n) {
    this.n = n;
  }

  @Override
  public RangeNTree copy() {
    return (RangeNTree) super.copy();
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
