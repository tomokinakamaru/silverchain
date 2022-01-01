package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class ChainStmtTree extends Tree<ChainStmtTree> {

  public TypeRefTree retType() {
    return children().find(TypeRefTree.class);
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
    return retType() + " " + expr() + ";";
  }
}
