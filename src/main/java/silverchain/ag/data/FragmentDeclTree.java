package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class FragmentDeclTree extends DeclTree<FragmentDeclTree> {

  private String id;

  public String id() {
    return id;
  }

  public void id(String id) {
    this.id = id;
  }

  public ChainExprTree expr() {
    return children().find(ChainExprTree.class);
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
    return id + " = " + expr() + ";";
  }
}
