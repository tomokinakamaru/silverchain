package silverchain.command;

import silverchain.SilverchainException;

public final class UnsupportedLanguage extends SilverchainException {

  UnsupportedLanguage(String language) {
    super("Unsupported language: %s", language);
  }
}
