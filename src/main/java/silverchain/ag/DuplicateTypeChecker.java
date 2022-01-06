package silverchain.ag;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.ag.data.TypeDeclTree;
import silverchain.ag.error.DuplicateType;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;

@API(status = API.Status.INTERNAL)
public class DuplicateTypeChecker implements TreeListener<Void> {

  protected Map<String, TypeDeclTree> declarations = new HashMap<>();

  @Override
  public void enter(TreeStack ancestors, TypeDeclTree tree, Void arg) {
    String id = tree.head().name().toString();
    if (declarations.containsKey(id)) {
      throw new DuplicateType(id, declarations.get(id), tree);
    } else {
      declarations.put(id, tree);
    }
  }
}
