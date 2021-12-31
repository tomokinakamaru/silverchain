package silverchain.java.data;

import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class VarRefNode implements ExpressionNode {

  private String name;

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
    return Stream.empty();
  }
}
