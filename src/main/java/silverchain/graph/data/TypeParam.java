package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.ag.data.TypeParamTree;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class TypeParam extends Attr {

  private String name;

  private Bounds bounds;

  public static TypeParam build(TypeParamTree tree) {
    if (tree == null) return null;
    TypeParam attr = new TypeParam();
    attr.name(tree.name());
    attr.bounds(Bounds.build(tree.bounds()));
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

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

  @Override
  public String toString() {
    return name + Objects.toString(bounds, "");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TypeParam typeParam = (TypeParam) o;
    return Objects.equals(name, typeParam.name) && Objects.equals(bounds, typeParam.bounds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, bounds);
  }
}
