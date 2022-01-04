package silverchain.graph.data;

import static java.util.stream.Collectors.toCollection;

import org.apiguardian.api.API;
import silverchain.ag.data.TypeArgsTree;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class TypeArgs extends Attrs<TypeArg> {

  public static TypeArgs build(TypeArgsTree tree) {
    if (tree == null) return null;
    TypeArgs attr = tree.stream().map(TypeArg::build).collect(toCollection(TypeArgs::new));
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
