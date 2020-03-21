package silverchain.codegen.java;

import silverchain.grammar.QualifiedName;
import silverchain.grammar.TypeArguments;
import silverchain.grammar.TypeReference;

final class Utility {

  private Utility() {}

  static String encodeTypeReference(TypeReference reference) {
    StringBuilder stringBuilder = new StringBuilder();

    // name
    stringBuilder.append(encodeQualifiedName(reference.name()));

    // type arguments
    if (reference.arguments() != null) {
      stringBuilder.append("<");
      TypeArguments as = reference.arguments();
      while (as != null) {
        stringBuilder.append(encodeTypeReference(as.head().reference()));
        as = as.tail();
        if (as != null) {
          stringBuilder.append(", ");
        }
      }
      stringBuilder.append(">");
    }

    return stringBuilder.toString();
  }

  static String encodeQualifiedName(QualifiedName name) {
    if (name.qualifier() == null) {
      return name.name();
    }
    return encodeQualifiedName(name.qualifier()) + "." + name.name();
  }
}
