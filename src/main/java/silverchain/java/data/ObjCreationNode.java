package silverchain.java.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class ObjCreationNode implements ExpressionNode {

  private TypeRefNode type;

  private ArgsNode arguments;

  public TypeRefNode type() {
    return type;
  }

  public void type(TypeRefNode type) {
    this.type = type;
  }

  public ArgsNode arguments() {
    return arguments;
  }

  public void arguments(ArgsNode arguments) {
    this.arguments = arguments;
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
    return Stream.of(type, arguments).filter(Objects::nonNull);
  }
}
