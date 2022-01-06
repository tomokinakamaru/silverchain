package silverchain.ag;

import java.util.HashSet;
import java.util.Set;
import org.apiguardian.api.API;
import silverchain.ag.data.FragmentDeclTree;
import silverchain.ag.data.FragmentRefTree;
import silverchain.ag.error.UndefinedFragment;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class UndefinedFragmentChecker implements TreeListener<Void> {

  protected Set<String> fragments = new HashSet<>();

  @Override
  public void enter(TreeStack ancestors, FragmentDeclTree tree, Void arg) {
    fragments.add(tree.id());
  }

  @Override
  public void enter(TreeStack ancestors, FragmentRefTree tree, Void arg) {
    String id = tree.id();
    if (!fragments.contains(id)) {
      throw new UndefinedFragment(id, tree);
    }
  }
}
