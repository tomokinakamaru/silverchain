package silverchain.graph.core;

import java.util.stream.Stream;
import org.apiguardian.api.API;
import silverchain.graph.walker.AttrListener;
import silverchain.srcmap.IntervalLists;

@API(status = API.Status.INTERNAL)
public interface Attr {

  Stream<? extends Attr> children();

  <T> void enter(AttrListener<T> listener, T arg);

  <T> void exit(AttrListener<T> listener, T arg);

  IntervalLists srcMap();

  void srcMap(IntervalLists srcMap);
}
