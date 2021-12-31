package silverchain.ag;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.ag.data.AliasDeclTree;
import silverchain.ag.error.AliasConflict;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class AliasConflictChecker implements TreeListener<Void> {

  protected Map<String, AliasDeclTree> aliases = new HashMap<>();

  @Override
  public void enter(AliasDeclTree tree, Void arg) {
    String id = tree.name().id();
    if (aliases.containsKey(id)) {
      throw new AliasConflict(aliases.get(id), tree);
    } else {
      aliases.put(id, tree);
    }
  }
}
