package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class TypeDeclTree extends TreeImpl implements DeclTree {

  public TypeDeclHeadTree head() {
    return children().find(TypeDeclHeadTree.class);
  }

  public TypeDeclBodyTree body() {
    return children().find(TypeDeclBodyTree.class);
  }

  @Override
  public TypeDeclTree copy() {
    return (TypeDeclTree) super.copy();
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
