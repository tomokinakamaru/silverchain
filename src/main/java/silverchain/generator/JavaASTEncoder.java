package silverchain.generator;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import silverchain.parser.Method;
import silverchain.parser.MethodParameter;
import silverchain.parser.MethodParameters;
import silverchain.parser.QualifiedName;
import silverchain.parser.TypeParameter;
import silverchain.parser.TypeParameterBound;
import silverchain.parser.TypeReference;
import silverchain.parser.TypeReferences;

final class JavaASTEncoder {

  private JavaASTEncoder() {}

  static String encodeAsDeclaration(List<TypeParameter> parameters) {
    return parameters.isEmpty() ? "" : "<" + csv(parameters.stream(), p -> encode(p, true)) + ">";
  }

  static String encodeAsArgument(List<TypeParameter> parameters) {
    return parameters.isEmpty() ? "" : "<" + csv(parameters.stream(), p -> encode(p, false)) + ">";
  }

  private static String encode(TypeParameter parameter, boolean includeBound) {
    String s = includeBound ? parameter.bound().map(JavaASTEncoder::encode).orElse("") : "";
    return parameter.name() + s;
  }

  private static String encode(TypeParameterBound bound) {
    return " extends " + encode(bound.reference());
  }

  static String encodeAsDeclaration(Method method) {
    return encode(method, true);
  }

  static String encodeAsInvocation(Method method) {
    return encode(method, false);
  }

  private static String encode(Method method, boolean includeType) {
    String s = method.parameters().map(p -> encode(p, includeType)).orElse("");
    return method.name() + "(" + s + ")";
  }

  private static String encode(MethodParameters parameters, boolean includeType) {
    return csv(parameters.stream(), p -> encode(p, includeType));
  }

  private static String encode(MethodParameter parameter, boolean includeType) {
    return includeType ? encode(parameter.type()) + " " + parameter.name() : parameter.name();
  }

  static String encode(TypeReference reference) {
    return encode(reference.name()) + reference.arguments().map(JavaASTEncoder::encode).orElse("");
  }

  private static String encode(TypeReferences arguments) {
    return "<" + csv(arguments.stream(), JavaASTEncoder::encode) + ">";
  }

  static String encode(QualifiedName name) {
    return String.join(".", name);
  }

  private static <T> String csv(Stream<T> stream, Function<T, String> function) {
    return stream.map(function).collect(Collectors.joining(", "));
  }
}
