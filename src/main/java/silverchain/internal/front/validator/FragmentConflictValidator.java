package silverchain.internal.front.validator;

import java.util.HashMap;
import java.util.Map;
import silverchain.internal.front.parser.AgBaseListener;
import silverchain.internal.front.parser.AgParser.FragmentDeclContext;

public class FragmentConflictValidator extends AgBaseListener {

  protected final Map<String, FragmentDeclContext> fragments = new HashMap<>();

  @Override
  public void enterFragmentDecl(FragmentDeclContext ctx) {
    String id = ctx.FRAGMENT_ID().getText();
    if (fragments.containsKey(id)) {
      throw new FragmentConflict(fragments.get(id), ctx);
    } else {
      fragments.put(id, ctx);
    }
  }
}
