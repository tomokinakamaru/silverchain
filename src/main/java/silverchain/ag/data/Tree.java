package silverchain.ag.data;

import org.apiguardian.api.API;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;
import silverchain.srcmap.IntervalList;

@API(status = API.Status.INTERNAL)
public interface Tree {

  <T> void enter(TreeStack ancestors, TreeListener<T> listener, T arg);

  <T> void exit(TreeStack ancestors, TreeListener<T> listener, T arg);

  Tree copy();

  TreeChildren children();

  void children(TreeChildren children);

  IntervalList srcMap();

  void srcMap(IntervalList srcMap);

  void addSrcMap(IntervalList srcMap);
}
