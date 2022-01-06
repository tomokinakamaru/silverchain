package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class QualifierTree extends TreeImpl {

  private String head;

  public String head() {
    return head;
  }

  public void head(String head) {
    this.head = head;
  }

  public QualifierTree tail() {
    return children().find(QualifierTree.class);
  }

  @Override
  public QualifierTree copy() {
    return (QualifierTree) super.copy();
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
