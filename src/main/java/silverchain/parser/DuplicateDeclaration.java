package silverchain.parser;

import silverchain.SilverchainException;

public final class DuplicateDeclaration extends SilverchainException {

  DuplicateDeclaration(TypeParameter parameter) {
    super("%s is already defined (%s)", parameter.name(), parameter.range().begin());
  }
}
