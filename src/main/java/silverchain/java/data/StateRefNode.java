package silverchain.java.data;

import java.util.Objects;
import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class StateRefNode extends TypeRefNode {

  private InterfaceNode state;

  public InterfaceNode state() {
    return state;
  }

  public void state(InterfaceNode state) {
    this.state = state;
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
    return Stream.of(state, typeArguments()).filter(Objects::nonNull);
  }
}
