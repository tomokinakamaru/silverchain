package silverchain.parser;

public final class ImportStatements extends ASTNodeN<ImportStatement, ImportStatements> {

  ImportStatements(Range range, ImportStatement head, ImportStatements tail) {
    super(range, head, tail);
  }
}
