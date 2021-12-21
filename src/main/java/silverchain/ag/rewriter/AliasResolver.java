package silverchain.ag.rewriter;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.ag.AgListener;
import silverchain.ag.antlr.AgParser.AliasDeclContext;
import silverchain.ag.antlr.AgParser.NameContext;

@API(status = API.Status.INTERNAL)
public class AliasResolver extends AgListener {

  protected Map<String, NameContext> aliases = new HashMap<>();

  @Override
  public void exitAliasDecl(AliasDeclContext ctx) {
    aliases.put(ctx.name().ID().getText(), ctx.name());
  }

  @Override
  public void exitName(NameContext ctx) {
    if (ctx.qualifier() == null) {
      String id = ctx.ID().getText();
      if (aliases.containsKey(id)) {
        NameContext c = AgReplicator.replicate(aliases.get(id));
        AgAssembler.replace(ctx.getParent(), ctx, c);
      }
    }
  }
}
