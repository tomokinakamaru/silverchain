package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class TypeParamTree extends TreeImpl {

  private String name;

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public BoundsTree bounds() {
    return children().find(BoundsTree.class);
  }

  @Override
  public TypeParamTree copy() {
    return (TypeParamTree) super.copy();
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
