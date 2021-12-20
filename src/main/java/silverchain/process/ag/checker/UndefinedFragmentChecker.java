package silverchain.process.ag.checker;

import java.util.HashSet;
import java.util.Set;
import org.apiguardian.api.API;
import silverchain.data.ag.visitor.AgTreeChecker;
import silverchain.process.ag.antlr.AgParser.FragmentDeclContext;
import silverchain.process.ag.antlr.AgParser.FragmentRefContext;
import silverchain.process.ag.checker.exception.UndefinedFragment;

@API(status = API.Status.INTERNAL)
public class UndefinedFragmentChecker extends AgTreeChecker {

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
