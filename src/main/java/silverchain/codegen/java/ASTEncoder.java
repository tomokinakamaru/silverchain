package silverchain.codegen.java;

import silverchain.grammar.Method;
import silverchain.grammar.MethodParameter;
import silverchain.grammar.MethodParameters;
import silverchain.grammar.QualifiedName;
import silverchain.grammar.TypeArgument;
import silverchain.grammar.TypeArguments;
import silverchain.grammar.TypeReference;

final class ASTEncoder {

  private ASTEncoder() {}

  static String encode(QualifiedName name) {
    if (name.qualifier() == null) {
      return name.name();
    }
    return encode(name.qualifier()) + "." + name.name();
  }

  static String encode(TypeReference reference) {
    String s = encode(reference.arguments());
    return s == null ? encode(reference.name()) : encode(reference.name()) + "<" + s + ">";
  }

  static String encode(TypeArguments arguments) {
    if (arguments == null) {
      return null;
    }
    if (arguments.tail() == null) {
      return encode(arguments.head());
    }
    return encode(arguments.head()) + ", " + encode(arguments.tail());
  }

  static String encode(TypeArgument argument) {
    return encode(argument.reference());
  }

  static String encode(Method method) {
    String s = encode(method.parameters());
    return method.name() + "(" + (s == null ? "" : s) + ")";
  }

  static String encode(MethodParameters parameters) {
    if (parameters == null) {
      return null;
    }
    if (parameters.tail() == null) {
      return encode(parameters.head());
    }
    return encode(parameters.head()) + ", " + encode(parameters.tail());
  }

  static String encode(MethodParameter parameter) {
    return encode(parameter.type()) + " " + parameter.name();
  }
}
