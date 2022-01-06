package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class ParamTree extends TreeImpl {

  private boolean varargs;

  private String name;

  public boolean varargs() {
    return varargs;
  }

  public void varargs(boolean varargs) {
    this.varargs = varargs;
  }

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public TypeRefTree type() {
    return children().find(TypeRefTree.class);
  }

  @Override
  public ParamTree copy() {
    return (ParamTree) super.copy();
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
