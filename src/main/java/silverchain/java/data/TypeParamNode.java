package silverchain.java.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class TypeParamNode implements Node {

  private String name;

  private BoundsNode bounds;

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public BoundsNode bounds() {
    return bounds;
  }

  public void bounds(BoundsNode bounds) {
    this.bounds = bounds;
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
    return Stream.of(bounds).filter(Objects::nonNull);
  }
}
