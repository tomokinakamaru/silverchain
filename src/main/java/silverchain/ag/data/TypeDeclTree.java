package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class TypeDeclTree extends DeclTree<TypeDeclTree> {

  public TypeDeclHeadTree head() {
    return children().find(TypeDeclHeadTree.class);
  }

  public TypeDeclBodyTree body() {
    return children().find(TypeDeclBodyTree.class);
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
    return head() + " " + body();
  }
}
