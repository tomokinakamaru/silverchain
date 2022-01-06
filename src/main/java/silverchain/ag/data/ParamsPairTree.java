package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class ParamsPairTree extends TreeImpl {

  public OuterParamsTree outerParams() {
    return children().find(OuterParamsTree.class);
  }

  public InnerParamsTree innerParams() {
    return children().find(InnerParamsTree.class);
  }

  @Override
  public ParamsPairTree copy() {
    return (ParamsPairTree) super.copy();
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
