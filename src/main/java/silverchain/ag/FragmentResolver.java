package silverchain.ag;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.ag.data.ChainExprTree;
import silverchain.ag.data.FragmentDeclTree;
import silverchain.ag.data.FragmentRefTree;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class FragmentResolver implements TreeListener<Void> {

  protected Map<String, ChainExprTree> fragments = new HashMap<>();

  @Override
  public void exit(FragmentDeclTree tree, Void arg) {
    fragments.put(tree.id(), tree.expr());
  }

  @Override
  public void exit(FragmentRefTree tree, Void arg) {
    ChainExprTree t = fragments.get(tree.id()).copy();
    t.add(tree.srcMap());
    tree.parent().children().replace(tree, t);
  }
}
