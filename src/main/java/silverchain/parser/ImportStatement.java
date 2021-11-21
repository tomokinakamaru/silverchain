package silverchain.parser;

public final class ImportStatement extends ASTNode1<QualifiedName> {

  public ImportStatement(Range range, QualifiedName child) {
    super(range, child);
  }

  public QualifiedName qualifiedName() {
    return child();
  }
}
