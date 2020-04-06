package silverchain.command;

public final class UnsupportedLanguage extends RuntimeException {

  UnsupportedLanguage(String language) {
    super("Unsupported language: " + language);
  }
}
