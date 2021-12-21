package silverchain.ag.checker;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.ag.AgListener;
import silverchain.ag.antlr.AgParser.FragmentDeclContext;

@API(status = API.Status.INTERNAL)
public class DuplicateFragmentChecker extends AgListener {

  protected Map<String, FragmentDeclContext> fragments = new HashMap<>();

  @Override
  public void enterFragmentDecl(FragmentDeclContext ctx) {
    String id = ctx.FRAGMENT_ID().getText();
    if (fragments.containsKey(id)) {
      throw new DuplicateFragment(fragments.get(id), ctx);
    } else {
      fragments.put(id, ctx);
    }
  }
}
