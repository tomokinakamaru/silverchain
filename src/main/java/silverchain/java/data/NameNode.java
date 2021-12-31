package silverchain.java.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class NameNode implements Node {

  private String id;

  private QualifierNode qualifier;

  public String id() {
    return id;
  }

  public void id(String id) {
    this.id = id;
  }

  public QualifierNode qualifier() {
    return qualifier;
  }

  public void qualifier(QualifierNode qualifier) {
    this.qualifier = qualifier;
  }

  @Override
  public <T> void enter(NodeListener<T> listener, T arg) {
    listener.enter(this, arg);
  }

  @Override
  public <T> void exit(NodeListener<T> listener, T arg) {
    listener.exit(this, arg);
  }

  @Override
  public Stream<? extends Node> children() {
    return Stream.of(qualifier).filter(Objects::nonNull);
  }
}
