package silverchain.internal.front.validator;

import java.util.HashSet;
import java.util.Set;
import silverchain.internal.front.parser.antlr.AgBaseListener;
import silverchain.internal.front.parser.antlr.AgParser.FragmentDeclContext;
import silverchain.internal.front.parser.antlr.AgParser.FragmentRefContext;

public class MissingFragmentValidator extends AgBaseListener {

  protected final Set<String> fragments = new HashSet<>();

  @Override
  public void enterFragmentDecl(FragmentDeclContext ctx) {
    fragments.add(ctx.FRAGMENT_ID().getText());
  }

  @Override
  public void enterFragmentRef(FragmentRefContext ctx) {
    String id = ctx.FRAGMENT_ID().getText();
    if (!fragments.contains(id)) {
      throw new MissingFragment(id, ctx);
    }
  }
}
