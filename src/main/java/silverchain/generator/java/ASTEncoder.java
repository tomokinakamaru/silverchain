package silverchain.generator.java;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import silverchain.parser.Method;
import silverchain.parser.MethodParameter;
import silverchain.parser.MethodParameters;
import silverchain.parser.QualifiedName;
import silverchain.parser.TypeParameter;
import silverchain.parser.TypeParameterBound;
import silverchain.parser.TypeReference;
import silverchain.parser.TypeReferences;

final class ASTEncoder {

  private ASTEncoder() {}

  /* Type parameter ----------------------------------------------------------------------------- */
  static String encodeAsDeclaration(List<TypeParameter> parameters) {
    return parameters.isEmpty() ? "" : "<" + csv(parameters, p -> encode(p, true)) + ">";
  }

  static String encodeAsArgument(List<TypeParameter> parameters) {
    return parameters.isEmpty() ? "" : "<" + csv(parameters, p -> encode(p, false)) + ">";
  }

  private static String encode(TypeParameter parameter, boolean includeBound) {
    return parameter.name()
        + (includeBound ? parameter.bound().map(ASTEncoder::encode).orElse("") : "");
  }

  private static String encode(TypeParameterBound bound) {
    return " extends " + encode(bound.reference());
  }

  /* Method ------------------------------------------------------------------------------------- */
  static String encodeAsDeclaration(Method method) {
    return encode(method, true);
  }

  static String encodeAsInvocation(Method method) {
    return encode(method, false);
  }

  private static String encode(Method method, boolean includeType) {
    return method.name()
        + "("
        + method.parameters().map(p -> encode(p, includeType)).orElse("")
        + ")";
  }

  private static String encode(MethodParameters parameters, boolean includeType) {
    return csv(parameters, p -> encode(p, includeType));
  }

  private static String encode(MethodParameter parameter, boolean includeType) {
    return includeType ? encode(parameter.type()) + " " + parameter.name() : parameter.name();
  }

  /* Type reference ----------------------------------------------------------------------------- */
  static String encode(TypeReference reference) {
    return encode(reference.name()) + reference.arguments().map(ASTEncoder::encode).orElse("");
  }

  private static String encode(TypeReferences arguments) {
    return "<" + csv(arguments, ASTEncoder::encode) + ">";
  }

  /* Qualified name ----------------------------------------------------------------------------- */
  static String encode(QualifiedName name) {
    return String.join(".", name);
  }

  /* Misc --------------------------------------------------------------------------------------- */
  private static <T> String csv(Iterable<T> iterable, Function<T, String> function) {
    List<String> list = new ArrayList<>();
    iterable.forEach(item -> list.add(function.apply(item)));
    return String.join(", ", list);
  }
}
