package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrVisitor;

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
  public <R, A> R accept(AttrVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
  }

  @Override
  public void enter(AttrListener listener) {
    listener.enter(this);
  }

  @Override
  public void exit(AttrListener listener) {
    listener.exit(this);
  }
}
