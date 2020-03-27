package silverchain.codegen.java;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import silverchain.grammar.Method;
import silverchain.grammar.MethodParameter;
import silverchain.grammar.MethodParameters;
import silverchain.grammar.QualifiedName;
import silverchain.grammar.TypeArgument;
import silverchain.grammar.TypeArguments;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeReference;

final class GrammarEncoder {

  /* Type parameter ----------------------------------------------------------------------------- */
  static String encode(List<TypeParameter> parameters) {
    return parameters.isEmpty() ? "" : "<" + csv(parameters, GrammarEncoder::encode) + ">";
  }

  private static String encode(TypeParameter parameter) {
    return parameter.name();
  }

  /* Method ------------------------------------------------------------------------------------- */
  static String encodeAsDeclaration(Method method) {
    return encode(method, true);
  }

  static String encodeAsInvocation(Method method) {
    return encode(method, false);
  }

  private static String encode(Method method, boolean includeType) {
    return method.name() + "(" + encode(method.parameters(), includeType) + ")";
  }

  private static String encode(MethodParameters parameters, boolean includeType) {
    return parameters == null ? "" : csv(parameters.stream(), p -> encode(p, includeType));
  }

  private static String encode(MethodParameter parameter, boolean includeType) {
    return includeType ? encode(parameter.type()) + " " + parameter.name() : parameter.name();
  }

  /* Type reference ----------------------------------------------------------------------------- */
  static String encode(TypeReference reference) {
    return encode(reference.name()) + encode(reference.arguments());
  }

  private static String encode(TypeArguments arguments) {
    return arguments == null ? "" : "<" + csv(arguments.stream(), GrammarEncoder::encode) + ">";
  }

  private static String encode(TypeArgument argument) {
    return encode(argument.reference());
  }

  /* Qualified name ----------------------------------------------------------------------------- */
  static String encode(QualifiedName name) {
    return name == null ? "" : String.join(".", name.list());
  }

  /* Misc --------------------------------------------------------------------------------------- */
  private static <T> String csv(List<T> list, Function<T, String> function) {
    return csv(list.stream(), function);
  }

  private static <T> String csv(Stream<T> stream, Function<T, String> function) {
    return stream.map(function).collect(joining(", "));
  }
}
