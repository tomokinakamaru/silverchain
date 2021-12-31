package silverchain.java.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class WildcardNode implements TypeArgNode {

  private BoundNode bound;

  public BoundNode bound() {
    return bound;
  }

  public void bound(BoundNode bound) {
    this.bound = bound;
  }

  @Override
  public <T> void enter(NodeListener<T> listener, T arg) {
    listener.enter(this, arg);
  }

  @Override
  public <T> void exit(NodeListener<T> listener, T arg) {
    listener.exit(this, arg);
  }

  @Override
  public Stream<? extends Node> children() {
    return Stream.of(bound).filter(Objects::nonNull);
  }
}
