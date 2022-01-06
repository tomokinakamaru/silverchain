package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class TypeDeclHeadTree extends TreeImpl {

  public NameTree name() {
    return children().find(NameTree.class);
  }

  public ParamsPairTree params() {
    return children().find(ParamsPairTree.class);
  }

  public NameTree originalName() {
    return children().findOriginal(NameTree.class);
  }

  @Override
  public TypeDeclHeadTree copy() {
    return (TypeDeclHeadTree) super.copy();
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
