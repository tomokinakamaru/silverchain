package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class TypeParam extends Attr {

  private String name;

  private Bounds bounds;

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public Bounds bounds() {
    return bounds;
  }

  public void bounds(Bounds bounds) {
    this.bounds = bounds;
  }

  @Override
  public Stream<? extends Attr> children() {
    return Stream.of(bounds).filter(Objects::nonNull);
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
