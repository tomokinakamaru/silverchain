package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class BoundTree extends Tree<BoundTree> {

  private boolean upperBound;

  public boolean upperBound() {
    return upperBound;
  }

  public void upperBound(boolean upperBound) {
    this.upperBound = upperBound;
  }

  public TypeRefTree type() {
    return children().find(TypeRefTree.class);
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
    return (upperBound ? " extends " : " super ") + type();
  }
}
