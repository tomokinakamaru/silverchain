package silverchain.ag;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.ag.data.FragmentDeclTree;
import silverchain.ag.error.DuplicateFragment;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class DuplicateFragmentChecker implements TreeListener<Void> {

  protected Map<String, FragmentDeclTree> fragments = new HashMap<>();

  @Override
  public void enter(TreeStack ancestors, FragmentDeclTree tree, Void arg) {
    String id = tree.id();
    if (fragments.containsKey(id)) {
      throw new DuplicateFragment(fragments.get(id), tree);
    } else {
      fragments.put(id, tree);
    }
  }
}
