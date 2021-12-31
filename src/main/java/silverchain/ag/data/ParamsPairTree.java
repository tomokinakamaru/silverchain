package silverchain.ag.data;

import java.util.Objects;
import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class ParamsPairTree extends Tree<ParamsPairTree> {

  public OuterParamsTree outerParams() {
    return children().find(OuterParamsTree.class);
  }

  public InnerParamsTree innerParams() {
    return children().find(InnerParamsTree.class);
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
    return "<" + Objects.toString(outerParams(), "") + Objects.toString(innerParams(), "") + ">";
  }
}
