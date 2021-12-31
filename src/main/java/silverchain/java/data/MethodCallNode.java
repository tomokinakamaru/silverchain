package silverchain.java.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class MethodCallNode implements ExpressionNode {

  private ExpressionNode receiver;

  private String name;

  private ArgsNode arguments;

  public ExpressionNode receiver() {
    return receiver;
  }

  public void receiver(ExpressionNode receiver) {
    this.receiver = receiver;
  }

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
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
    return Stream.of(receiver, arguments).filter(Objects::nonNull);
  }
}
