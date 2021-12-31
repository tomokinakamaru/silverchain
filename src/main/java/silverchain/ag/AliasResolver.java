package silverchain.ag;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.ag.data.AliasDeclTree;
import silverchain.ag.data.NameTree;
import silverchain.ag.walker.TreeListener;

@API(status = API.Status.INTERNAL)
public class AliasResolver implements TreeListener<Void> {

  protected Map<String, NameTree> aliases = new HashMap<>();

  @Override
  public void exit(AliasDeclTree tree, Void arg) {
    aliases.put(tree.name().id(), tree.name());
  }

  @Override
  public void exit(NameTree tree, Void arg) {
    if (tree.qualifier() == null) {
      String id = tree.id();
      if (aliases.containsKey(id)) {
        NameTree t = aliases.get(id).copy();
        tree.parent().children().replace(tree, t);
      }
    }
  }
}
