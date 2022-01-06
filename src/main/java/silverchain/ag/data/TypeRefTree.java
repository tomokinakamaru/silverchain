package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class TypeRefTree extends TreeImpl implements TypeArgTree {

  private int dim;

  public int dim() {
    return dim;
  }

  public void dim(int dim) {
    this.dim = dim;
  }

  public NameTree name() {
    return children().find(NameTree.class);
  }

  public TypeArgsTree args() {
    return children().find(TypeArgsTree.class);
  }

  public NameTree originalName() {
    return children().findOriginal(NameTree.class);
  }

  @Override
  public TypeRefTree copy() {
    return (TypeRefTree) super.copy();
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
