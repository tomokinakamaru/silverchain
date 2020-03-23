package silverchain.codegen.java;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import silverchain.codegen.GeneratedFile;
import silverchain.grammar.Method;
import silverchain.grammar.MethodParameter;
import silverchain.grammar.MethodParameters;
import silverchain.grammar.QualifiedName;
import silverchain.grammar.TypeArgument;
import silverchain.grammar.TypeArguments;
import silverchain.grammar.TypeReference;
import silverchain.graph.GraphEdge;
import silverchain.graph.GraphNode;

public final class JavaGenerator {

  private final GraphAnalyzer analyzer;

  public JavaGenerator(List<GraphNode> nodes) {
    this.analyzer = new GraphAnalyzer(nodes);
  }

  public List<GeneratedFile> generate() {
    return analyzer.nodes().stream().map(this::generate).collect(Collectors.toList());
  }

  private GeneratedFile generate(GraphNode node) {
    CodeBuilder codeBuilder = new CodeBuilder();

    codeBuilder
        .append("package ", encode(analyzer.packageName(node)), ";\n\n")
        .append("public interface ")
        .append(analyzer.name(node))
        .append("<", node.tags(), ", ", ">")
        .append(" {");

    for (GraphEdge edge : node.edges()) {
      codeBuilder
          .append("\n  ")
          .append("<", analyzer.tags(edge), ", ", "> ")
          .append(encode(analyzer.destination(edge)))
          .append(" ")
          .append(encode(edge.label().as(Method.class)))
          .append(";\n");
    }

    codeBuilder.append("}\n");

    return codeBuilder.generate(path(analyzer.qualifiedName(node)));
  }

  private static Path path(QualifiedName name) {
    return Paths.get(String.join(File.separator, encode(name).split("\\.")) + ".java");
  }

  private static String encode(QualifiedName name) {
    if (name == null) {
      return null;
    }
    String qualifier = encode(name.qualifier());
    String text = name.name();
    return qualifier == null ? text : qualifier + "." + text;
  }

  private static String encode(Method method) {
    String parameters = encode(method.parameters());
    return method.name() + "(" + (parameters == null ? "" : parameters) + ")";
  }

  private static String encode(MethodParameters parameters) {
    if (parameters == null) {
      return null;
    }
    String head = encode(parameters.head());
    String tail = encode(parameters.tail());
    return tail == null ? head : head + ", " + tail;
  }

  private static String encode(MethodParameter parameter) {
    return encode(parameter.type()) + " " + parameter.name();
  }

  private static String encode(TypeReference reference) {
    if (reference == null) {
      return "void";
    }
    String name = encode(reference.name());
    String args = encode(reference.arguments());
    return args == null ? name : name + "<" + args + ">";
  }

  private static String encode(TypeArguments arguments) {
    if (arguments == null) {
      return null;
    }
    String head = encode(arguments.head());
    String tail = encode(arguments.tail());
    return tail == null ? head : head + ", " + tail;
  }

  private static String encode(TypeArgument argument) {
    return encode(argument.reference());
  }
}
