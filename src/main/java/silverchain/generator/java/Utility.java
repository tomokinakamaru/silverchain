package silverchain.generator.java;

import static silverchain.generator.java.GrammarEncoder.encode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import silverchain.grammar.Method;
import silverchain.grammar.MethodParameter;

final class Utility {

  private Utility() {}

  static String filePath(String name) {
    return name.replaceAll("\\.", "/") + ".java";
  }

  static String packageDeclaration(String name) {
    return name.isEmpty() ? "" : "package " + name + ";\n\n";
  }

  static String qualifiedName(String qualifier, String name) {
    return qualifier.isEmpty() ? name : qualifier + "." + name;
  }

  static int countUniqueSignatures(List<JavaTransition> transitions) {
    Set<String> signatures = new HashSet<>();
    for (JavaTransition transition : transitions) {
      signatures.add(signature(transition.method()));
    }
    return signatures.size();
  }

  private static String signature(Method method) {
    List<String> types = new ArrayList<>();
    if (method.parameters() != null) {
      for (MethodParameter parameter : method.parameters()) {
        if (parameter.type().referent() == null) {
          types.add(encode(parameter.type().name()));
        } else {
          types.add("*");
        }
      }
    }
    return method.name() + "(" + String.join(", ", types) + ")";
  }
}
