package silverchain.graph.data;

import static java.util.stream.Collectors.toCollection;

import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.ag.data.SetTree;
import silverchain.ag.data.TypeParamTree;
import silverchain.graph.core.AttrsImpl;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class TypeParams extends AttrsImpl<TypeParam> {

  public static TypeParams build(SetTree<TypeParamTree> tree) {
    if (tree == null) return null;
    return tree.stream().map(TypeParam::build).collect(toCollection(TypeParams::new));
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
    return "<" + stream().map(TypeParam::toString).collect(Collectors.joining(", ")) + ">";
  }
}
