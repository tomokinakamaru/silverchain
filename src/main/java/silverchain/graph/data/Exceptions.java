package silverchain.graph.data;

import static java.util.stream.Collectors.toCollection;

import org.apiguardian.api.API;
import silverchain.ag.data.ExceptionsTree;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class Exceptions extends Attrs<TypeRef> {

  public static Exceptions build(ExceptionsTree tree) {
    if (tree == null) return null;
    Exceptions attr = tree.stream().map(TypeRef::build).collect(toCollection(Exceptions::new));
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

  @Override
  public <T> void enter(AttrListener<T> listener, T arg) {
    listener.enter(this, arg);
  }

  @Override
  public <T> void exit(AttrListener<T> listener, T arg) {
    listener.exit(this, arg);
  }
}
