package silverchain.process.ag.checker;

import java.util.HashMap;
import java.util.Map;
import org.apiguardian.api.API;
import silverchain.data.ag.visitor.AgTreeChecker;
import silverchain.process.ag.antlr.AgParser.ImportDeclContext;
import silverchain.process.ag.checker.exception.ImportConflict;

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
