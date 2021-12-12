package silverchain.internal.frontend.checker;

import java.util.HashMap;
import java.util.Map;
import silverchain.internal.frontend.parser.antlr.AgBaseListener;
import silverchain.internal.frontend.parser.antlr.AgParser.ImportDeclContext;

public class ImportConflictChecker extends AgBaseListener {

  protected final Map<String, ImportDeclContext> imports = new HashMap<>();

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
