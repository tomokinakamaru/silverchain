package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrVisitor;

@API(status = API.Status.INTERNAL)
public class Wildcard extends TypeArg {

  private Bound bound;

  public Bound bound() {
    return bound;
  }

  public void bound(Bound bound) {
    this.bound = bound;
  }

  @Override
  public Stream<? extends Attr> children() {
    return Stream.of(bound).filter(Objects::nonNull);
  }

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
