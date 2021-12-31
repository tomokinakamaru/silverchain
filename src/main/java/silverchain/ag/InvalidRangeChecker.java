package silverchain.ag;

import org.apiguardian.api.API;
import silverchain.ag.data.RangeNMTree;
import silverchain.ag.error.InvalidRange;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class InvalidRangeChecker implements TreeListener<Void> {

  @Override
  public void enter(RangeNMTree tree, Void arg) {
    if (tree.min() > tree.max()) {
      throw new InvalidRange(tree);
    }
  }
}
