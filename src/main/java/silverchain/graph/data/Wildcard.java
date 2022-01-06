package silverchain.graph.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.ag.data.WildcardTree;
import silverchain.graph.core.AttrImpl;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class Wildcard extends AttrImpl implements TypeArg {

  private Bound bound;

  public static Wildcard build(WildcardTree tree) {
    if (tree == null) return null;
    Wildcard attr = new Wildcard();
    attr.bound(Bound.build(tree.bound()));
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

  public Bound bound() {
    return bound;
  }

  public void bound(Bound bound) {
    this.bound = bound;
  }

  @Override
  public Stream<? extends AttrImpl> children() {
    return Stream.of(bound).filter(Objects::nonNull);
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
    return "?" + Objects.toString(bound, "");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Wildcard wildcard = (Wildcard) o;
    return Objects.equals(bound, wildcard.bound);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bound);
  }
}
