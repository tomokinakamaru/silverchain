package silverchain.ag.data;

import java.util.Objects;
import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class ChainFactTree extends Tree<ChainFactTree> {

  public ChainElemTree<?> elem() {
    return children().find(ChainElemTree.class);
  }

  public QuantifierTree<?> quantifier() {
    return children().find(QuantifierTree.class);
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
    return elem() + Objects.toString(quantifier(), "");
  }
}
