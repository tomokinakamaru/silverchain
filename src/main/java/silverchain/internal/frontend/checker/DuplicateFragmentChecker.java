package silverchain.internal.frontend.checker;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.FragmentDeclContext;
import silverchain.internal.frontend.checker.exception.DuplicateFragment;
import silverchain.internal.frontend.core.AgTreeChecker;

@API(status = API.Status.INTERNAL)
public class DuplicateFragmentChecker extends AgTreeChecker {

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
