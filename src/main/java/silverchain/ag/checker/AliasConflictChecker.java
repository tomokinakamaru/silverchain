package silverchain.ag.checker;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.ag.AgListener;
import silverchain.ag.antlr.AgParser.AliasDeclContext;

@API(status = API.Status.INTERNAL)
public class AliasConflictChecker extends AgListener {

  protected Map<String, AliasDeclContext> aliases = new HashMap<>();

  @Override
  public void enterAliasDecl(AliasDeclContext ctx) {
    String id = ctx.name().ID().getText();
    if (aliases.containsKey(id)) {
      throw new AliasConflict(aliases.get(id), ctx);
    } else {
      aliases.put(id, ctx);
    }
  }
}
