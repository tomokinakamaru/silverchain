package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class RetType extends EdgeAttr {

  private TypeRef ref;

  public TypeRef ref() {
    return ref;
  }

  public void ref(TypeRef ref) {
    this.ref = ref;
  }

  @Override
  public Stream<? extends Attr> children() {
    return Stream.of(ref).filter(Objects::nonNull);
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
