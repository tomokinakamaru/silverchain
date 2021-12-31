package silverchain.ag.data;

import java.util.Objects;
import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class TypeDeclHeadTree extends Tree<TypeDeclHeadTree> {

  public NameTree name() {
    return children().find(NameTree.class);
  }

  public ParamsPairTree params() {
    return children().find(ParamsPairTree.class);
  }

  public NameTree originalName() {
    return children().original(NameTree.class);
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
    return name() + Objects.toString(params(), "");
  }
}
