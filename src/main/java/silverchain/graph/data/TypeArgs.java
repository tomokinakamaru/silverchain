package silverchain.graph.data;

import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrVisitor;

@API(status = API.Status.INTERNAL)
public class TypeArgs extends Attrs<TypeArg> {

  @Override
  public <R, A> R accept(AttrVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
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
