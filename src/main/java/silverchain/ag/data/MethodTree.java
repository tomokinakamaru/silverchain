package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class MethodTree extends TreeImpl implements ChainElemTree {

  private String name;

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public LocalParamsTree typeParams() {
    return children().find(LocalParamsTree.class);
  }

  public ParamsTree params() {
    return children().find(ParamsTree.class);
  }

  public ExceptionsTree exceptions() {
    return children().find(ExceptionsTree.class);
  }

  @Override
  public MethodTree copy() {
    return (MethodTree) super.copy();
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
