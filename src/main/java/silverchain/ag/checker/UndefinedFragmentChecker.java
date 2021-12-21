package silverchain.ag.checker;

import java.util.HashSet;
import java.util.Set;
import org.apiguardian.api.API;
import silverchain.ag.AgListener;
import silverchain.ag.antlr.AgParser.FragmentDeclContext;
import silverchain.ag.antlr.AgParser.FragmentRefContext;

@API(status = API.Status.INTERNAL)
public class UndefinedFragmentChecker extends AgListener {

  protected Set<String> fragments = new HashSet<>();

  @Override
  public void enterFragmentDecl(FragmentDeclContext ctx) {
    fragments.add(ctx.FRAGMENT_ID().getText());
  }

  @Override
  public void enterFragmentRef(FragmentRefContext ctx) {
    String id = ctx.FRAGMENT_ID().getText();
    if (!fragments.contains(id)) {
      throw new UndefinedFragment(id, ctx);
    }
  }
}
