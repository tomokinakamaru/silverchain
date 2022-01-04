package silverchain.graph.data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.ag.data.NameTree;
import silverchain.graph.walker.AttrListener;

@API(status = API.Status.INTERNAL)
public class Name extends Attr {

  private String id;

  private List<String> qualifier;

  public static Name build(NameTree tree) {
    if (tree == null) return null;
    Name attr = new Name();
    attr.id(tree.id());
    attr.qualifier(tree.qualifier());
    attr.srcMap().add(tree.srcMap());
    return attr;
  }

  public String id() {
    return id;
  }

  public void id(String id) {
    this.id = id;
  }

  public List<String> qualifier() {
    return qualifier;
  }

  public void qualifier(List<String> qualifier) {
    this.qualifier = qualifier;
  }

  @Override
  public Stream<? extends Attr> children() {
    return Stream.empty();
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
    return (qualifier == null ? "" : String.join(".", qualifier) + ".") + id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Name name = (Name) o;
    return Objects.equals(id, name.id) && Objects.equals(qualifier, name.qualifier);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, qualifier);
  }
}
