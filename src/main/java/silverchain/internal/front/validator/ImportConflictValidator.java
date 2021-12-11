package silverchain.internal.front.validator;

import java.util.HashMap;
import java.util.Map;
import silverchain.internal.front.parser.AgBaseListener;
import silverchain.internal.front.parser.AgParser.ImportDeclContext;

public class ImportConflictValidator extends AgBaseListener {

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
