package silverchain.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Input extends ASTNode2<ImportStatements, Grammars> {

  public Input(Range range, ImportStatements left, Grammars right) {
    super(range, left, right);
  }

  public Optional<ImportStatements> importStatements() {
    return Optional.ofNullable(left());
  }

  public Grammars grammars() {
    return right();
  }

  public Map<String, QualifiedName> importMap() {
    Map<String, QualifiedName> map = new HashMap<>();
    if (importStatements().isPresent()) {
      for (ImportStatement i : importStatements().get()) {
        QualifiedName fullName = i.qualifiedName();
        if (fullName.qualifier().isPresent()) {
          String name = fullName.name();
          if (map.containsKey(name)) {
            throw new DuplicateImport(i);
          } else {
            map.put(name, fullName);
          }
        }
      }
    }
    return map;
  }
}
