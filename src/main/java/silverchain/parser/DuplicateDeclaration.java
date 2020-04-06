package silverchain.parser;

public final class DuplicateDeclaration extends RuntimeException {

  DuplicateDeclaration(TypeParameter parameter) {
    super(
        String.format(
            "%s is already defined (L%dC%d)",
            parameter.name(),
            parameter.range().begin().line(),
            parameter.range().begin().column()));
  }
}
