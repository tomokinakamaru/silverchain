package silverchain.internal.frontend.checker;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.ImportDeclContext;
import silverchain.internal.frontend.checker.exception.ImportConflict;
import silverchain.internal.frontend.core.AgTreeChecker;

@API(status = API.Status.INTERNAL)
public class ImportConflictChecker extends AgTreeChecker {

  protected Map<String, ImportDeclContext> imports = new HashMap<>();

  @Override
  public void enterImportDecl(ImportDeclContext ctx) {
    String id = ctx.name().ID().getText();
    if (imports.containsKey(id)) {
      throw new ImportConflict(imports.get(id), ctx);
    } else {
      imports.put(id, ctx);
    }
  }
}
