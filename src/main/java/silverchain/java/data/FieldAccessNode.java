package silverchain.java.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class FieldAccessNode implements ExpressionNode {

  private ExpressionNode receiver;

  private String name;

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
    return Stream.of(receiver).filter(Objects::nonNull);
  }
}
