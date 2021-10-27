package silverchain.parser;

import silverchain.SilverchainException;

public final class DuplicateImport extends SilverchainException {

  DuplicateImport(ImportStatement stmt) {
    super("%s is already imported (%s)", stmt.qualifiedName().name(), stmt.range().begin());
  }
}
