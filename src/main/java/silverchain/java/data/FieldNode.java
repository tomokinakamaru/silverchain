package silverchain.java.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class FieldNode implements Node {

  private TypeRefNode type;

  private String name;

  public TypeRefNode type() {
    return type;
  }

  public void type(TypeRefNode type) {
    this.type = type;
  }

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
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
    return Stream.of(type).filter(Objects::nonNull);
  }
}