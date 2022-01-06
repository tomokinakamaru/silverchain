package silverchain.graph.data;

import static java.util.stream.Collectors.toCollection;

import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.ag.data.BoundsTree;
import silverchain.graph.core.AttrsImpl;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class Bounds extends AttrsImpl<TypeRef> {

  public static Bounds build(BoundsTree tree) {
    if (tree == null) return null;
    Bounds attr = tree.stream().map(TypeRef::build).collect(toCollection(Bounds::new));
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

  @Override
  public String toString() {
    return stream().map(TypeRef::toString).collect(Collectors.joining(" & "));
  }
}
