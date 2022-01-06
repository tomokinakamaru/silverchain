package silverchain.ag;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.ag.data.ChainExprTree;
import silverchain.ag.data.FragmentDeclTree;
import silverchain.ag.data.FragmentRefTree;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class FragmentResolver implements TreeListener<Void> {

  protected Map<String, ChainExprTree> fragments = new HashMap<>();

  @Override
  public void exit(TreeStack ancestors, FragmentDeclTree tree, Void arg) {
    fragments.put(tree.id(), tree.expr());
  }

  @Override
  public void exit(TreeStack ancestors, FragmentRefTree tree, Void arg) {
    ChainExprTree t = fragments.get(tree.id()).copy();
    t.addSrcMap(tree.srcMap());
    ancestors.peek().children().replace(tree, t);
  }
}
