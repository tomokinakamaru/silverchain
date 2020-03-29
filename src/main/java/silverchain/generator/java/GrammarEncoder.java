package silverchain.generator.java;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import silverchain.grammar.Method;
import silverchain.grammar.MethodParameter;
import silverchain.grammar.MethodParameters;
import silverchain.grammar.QualifiedName;
import silverchain.grammar.TypeArgument;
import silverchain.grammar.TypeArguments;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeParameterBound;
import silverchain.grammar.TypeReference;

final class GrammarEncoder {

  private GrammarEncoder() {}

  /* Type parameter ----------------------------------------------------------------------------- */
  static String encodeAsDeclaration(List<TypeParameter> parameters) {
    return parameters.isEmpty() ? "" : "<" + csv(parameters, p -> encode(p, true)) + ">";
  }

  static String encodeAsArgument(List<TypeParameter> parameters) {
    return parameters.isEmpty() ? "" : "<" + csv(parameters, p -> encode(p, false)) + ">";
  }

  private static String encode(TypeParameter parameter, boolean includeBound) {
    return parameter.name()
        + (includeBound ? parameter.bound().map(GrammarEncoder::encode).orElse("") : "");
  }

  private static String encode(TypeParameterBound bound) {
    if (bound == null) {
      return "";
    }
    return " " + (bound.isUpper() ? "extends" : "super") + " " + encode(bound.reference());
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
    return parameters == null ? "" : csv(parameters, p -> encode(p, includeType));
  }

  private static String encode(MethodParameter parameter, boolean includeType) {
    return includeType ? encode(parameter.type()) + " " + parameter.name() : parameter.name();
  }

  /* Type reference ----------------------------------------------------------------------------- */
  static String encode(TypeReference reference) {
    return encode(reference.name()) + reference.arguments().map(GrammarEncoder::encode).orElse("");
  }

  private static String encode(TypeArguments arguments) {
    return arguments == null ? "" : "<" + csv(arguments, GrammarEncoder::encode) + ">";
  }

  private static String encode(TypeArgument argument) {
    return encode(argument.reference());
  }

  /* Qualified name ----------------------------------------------------------------------------- */
  static String encode(QualifiedName name) {
    return name == null ? "" : String.join(".", name.list());
  }

  /* Misc --------------------------------------------------------------------------------------- */
  private static <T> String csv(Iterable<T> iterable, Function<T, String> function) {
    List<String> list = new ArrayList<>();
    iterable.forEach(item -> list.add(function.apply(item)));
    return String.join(", ", list);
  }
}
