package silverchain.ag.data;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class TypeRefTree extends TypeArgTree<TypeRefTree> {

  private int dim;

  public int dim() {
    return dim;
  }

  public void dim(int dim) {
    this.dim = dim;
  }

  public NameTree name() {
    return children().find(NameTree.class);
  }

  public TypeArgsTree args() {
    return children().find(TypeArgsTree.class);
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
    return name().toString()
        + Objects.toString(args(), "")
        + IntStream.range(0, dim).mapToObj(i -> "[]").collect(Collectors.joining());
  }
}
