package silverchain.java.data;

import java.util.stream.Stream;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface Node {

  <T> void enter(NodeListener<T> listener, T arg);

  <T> void exit(NodeListener<T> listener, T arg);

  Stream<? extends Node> children();
}
