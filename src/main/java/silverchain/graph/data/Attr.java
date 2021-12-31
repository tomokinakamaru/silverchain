package silverchain.graph.data;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.graph.walker.AttrVisitor;

@API(status = API.Status.INTERNAL)
public interface Attr {

  Stream<? extends Attr> children();

  <R, A> R accept(AttrVisitor<R, A> visitor, A arg);

  void enter(AttrListener listener);

  void exit(AttrListener listener);
}
