package silverchain.graph.data;

import static java.util.stream.Collectors.toCollection;

import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.ag.data.ParamsTree;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class Params extends Attrs<Param> {

  public static Params build(ParamsTree tree) {
    if (tree == null) return null;
    Params attr = tree.stream().map(Param::build).collect(toCollection(Params::new));
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
    return "(" + stream().map(Param::toString).collect(Collectors.joining(", ")) + ")";
  }
}
