package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class RangeNXTree extends RangeTree<RangeNXTree> {

  private int min;

  public int min() {
    return min;
  }

  public void min(int min) {
    this.min = min;
  }

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
    return "[" + min + ",]";
  }
}
