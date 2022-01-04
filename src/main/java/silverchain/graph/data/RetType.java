package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.ag.data.TypeRefTree;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class RetType extends EdgeAttr {

  private TypeRef ref;

  public static RetType build(TypeRefTree tree) {
    if (tree == null) return null;
    RetType attr = new RetType();
    attr.ref(TypeRef.build(tree));
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

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

  @Override
  public String toString() {
    return ref.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RetType retType = (RetType) o;
    return Objects.equals(ref, retType.ref);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ref);
  }
}
