package silverchain.ag.data;

import java.util.Objects;
import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class MethodTree extends ChainElemTree<MethodTree> {

  private String name;

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public TypeParamsTree typeParams() {
    return children().find(TypeParamsTree.class);
  }

  public ParamsTree params() {
    return children().find(ParamsTree.class);
  }

  public ExceptionsTree exceptions() {
    return children().find(ExceptionsTree.class);
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
    return name
        + Objects.toString(typeParams(), "")
        + params()
        + Objects.toString(exceptions(), "");
  }
}
