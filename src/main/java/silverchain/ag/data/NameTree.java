package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class NameTree extends TreeImpl {

  private String id;

  public String id() {
    return id;
  }

  public void id(String id) {
    this.id = id;
  }

  public QualifierTree qualifier() {
    return children().find(QualifierTree.class);
  }

  @Override
  public NameTree copy() {
    return (NameTree) super.copy();
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
