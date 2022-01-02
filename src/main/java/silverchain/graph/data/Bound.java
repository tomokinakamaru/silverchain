package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class Bound extends Attr {

  private boolean upperBound;

  private TypeRef type;

  public boolean upperBound() {
    return upperBound;
  }

  public void upperBound(boolean upperBound) {
    this.upperBound = upperBound;
  }

  public TypeRef type() {
    return type;
  }

  public void type(TypeRef type) {
    this.type = type;
  }

  @Override
  public Stream<? extends Attr> children() {
    return Stream.of(type).filter(Objects::nonNull);
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
