package silverchain.ag;

import org.apiguardian.api.API;
import silverchain.ag.data.RangeNMTree;
import silverchain.ag.data.RangeNTree;
import silverchain.ag.error.ZeroRepeat;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class ZeroRepeatChecker implements TreeListener<Void> {

  @Override
  public void enter(TreeStack ancestors, RangeNTree tree, Void arg) {
    if (tree.n() == 0) {
      throw new ZeroRepeat(tree);
    }
  }

  @Override
  public void enter(TreeStack ancestors, RangeNMTree tree, Void arg) {
    if (tree.max() == 0) {
      throw new ZeroRepeat(tree);
    }
  }
}
