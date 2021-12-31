package silverchain.graph.data;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrVisitor;

@API(status = API.Status.INTERNAL)
public class Name implements Attr {

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
  public void enter(AttrListener listener) {
    listener.enter(this);
  }

  @Override
  public void exit(AttrListener listener) {
    listener.exit(this);
  }
}
