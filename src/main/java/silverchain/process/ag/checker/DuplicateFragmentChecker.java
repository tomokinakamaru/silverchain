package silverchain.process.ag.checker;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.data.ag.visitor.AgTreeChecker;
import silverchain.process.ag.antlr.AgParser.FragmentDeclContext;
import silverchain.process.ag.checker.exception.DuplicateFragment;

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
