package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class ParamTree extends Tree<ParamTree> {

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
  public <T> void enter(TreeListener<T> listener, T arg) {
    listener.enter(this, arg);
  }

  @Override
  public <T> void exit(TreeListener<T> listener, T arg) {
    listener.exit(this, arg);
  }

  @Override
  public String toString() {
    return type() + (varargs ? "... " : " ") + name;
  }
}
