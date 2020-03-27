package silverchain.codegen.java;

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
}
