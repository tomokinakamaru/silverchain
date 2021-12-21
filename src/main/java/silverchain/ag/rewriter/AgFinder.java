package silverchain.ag.rewriter;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apiguardian.api.API;
import silverchain.ag.AgListener;

@API(status = API.Status.INTERNAL)
public class AgFinder<T extends ParserRuleContext> extends AgListener {

  protected Class<T> cls;

  protected List<T> result = new ArrayList<>();

  protected AgFinder(Class<T> cls) {
    this.cls = cls;
  }

  public static <T extends ParserRuleContext> List<T> find(Class<T> cls, ParserRuleContext ctx) {
    AgFinder<T> finder = new AgFinder<>(cls);
    ParseTreeWalker.DEFAULT.walk(finder, ctx);
    return finder.result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void enterEveryRule(ParserRuleContext ctx) {
    if (cls.isInstance(ctx)) {
      result.add((T) ctx);
    }
  }
}
