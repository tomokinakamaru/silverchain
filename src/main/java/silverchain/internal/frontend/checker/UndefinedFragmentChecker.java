package silverchain.internal.frontend.checker;

import java.util.HashSet;
import java.util.Set;
import silverchain.internal.frontend.parser.antlr.AgBaseListener;
import silverchain.internal.frontend.parser.antlr.AgParser.FragmentDeclContext;
import silverchain.internal.frontend.parser.antlr.AgParser.FragmentRefContext;

public class UndefinedFragmentChecker extends AgBaseListener {

  protected final Set<String> fragments = new HashSet<>();

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
