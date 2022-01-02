package silverchain.graph.data;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrVisitor;

@API(status = API.Status.INTERNAL)
public class Name extends Attr {

  private String id;

  private String qualifier;

  public String id() {
    return id;
  }

  public void id(String id) {
    this.id = id;
  }

  public String qualifier() {
    return qualifier;
  }

  public void qualifier(String qualifier) {
    this.qualifier = qualifier;
  }

  @Override
  public Stream<? extends Attr> children() {
    return Stream.empty();
  }

  @Override
  public <R, A> R accept(AttrVisitor<R, A> visitor, A arg) {
    return visitor.visit(this, arg);
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
