package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class ChainStmtTree extends TreeImpl {

  public TypeRefTree type() {
    return children().find(TypeRefTree.class);
  }

  public ChainExprTree expr() {
    return children().find(ChainExprTree.class);
  }

  @Override
  public ChainStmtTree copy() {
    return (ChainStmtTree) super.copy();
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
