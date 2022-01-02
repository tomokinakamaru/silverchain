package silverchain.java.data;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class TypeArgsNode extends Nodes<TypeArgNode> {

  @Override
  public <T> void enter(NodeListener<T> listener, T arg) {
    listener.enter(this, arg);
  }

  @Override
  public <T> void exit(NodeListener<T> listener, T arg) {
    listener.exit(this, arg);
  }
}