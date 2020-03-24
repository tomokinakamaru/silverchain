package silverchain.codegen.java;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import silverchain.codegen.GeneratedFile;
import silverchain.grammar.Method;
import silverchain.grammar.MethodParameter;
import silverchain.grammar.MethodParameters;
import silverchain.grammar.QualifiedName;
import silverchain.grammar.TypeArgument;
import silverchain.grammar.TypeArguments;
import silverchain.grammar.TypeParameter;
import silverchain.grammar.TypeReference;
import silverchain.graph.GraphEdge;
import silverchain.graph.GraphNode;

public final class JavaGenerator {

  private final GraphAnalyzer analyzer;

  public JavaGenerator(List<GraphNode> nodes) {
    this.analyzer = new GraphAnalyzer(nodes);
  }

  public List<GeneratedFile> generate() {
    List<GeneratedFile> files = new ArrayList<>();
    analyzer.nodes().stream().map(this::generateApi).forEach(files::add);
    analyzer.nodes().stream().map(this::generateImpl).forEach(files::add);
    files.add(generateListener());
    return files;
  }

  private GeneratedFile generateApi(GraphNode node) {
    CodeBuilder codeBuilder = new CodeBuilder();

    codeBuilder
        .append("package ", encode(analyzer.apiPackageName(node)), ";\n\n")
        .append(analyzer.apiModifier(node))
        .append("interface ")
        .append(analyzer.apiName(node))
        .append("<", encode(analyzer.tags(node)), ">")
        .append(" {");

    for (GraphEdge edge : node.edges()) {
      codeBuilder
          .append("\n  ")
          .append("<", encode(analyzer.tags(edge)), "> ")
          .append(encode(analyzer.destination(edge)))
          .append(" ")
          .append(encode(edge.label().as(Method.class)))
          .append(";\n");
    }

    codeBuilder.append("}\n");

    return codeBuilder.generate(path(analyzer.apiQualifiedName(node)));
  }

  private GeneratedFile generateImpl(GraphNode node) {
    String listenerName = analyzer.name() + "Listener";

    CodeBuilder codeBuilder = new CodeBuilder();

    codeBuilder
        .append("package ", encode(analyzer.packageName()), ";\n\n")
        .append(analyzer.implModifier(node))
        .append("class ")
        .append(analyzer.implName(node))
        .append("<", encode(analyzer.tags(node)), ">")
        .append(" implements ")
        .append(encode(analyzer.apiQualifiedName(node)))
        .append("<", encode(analyzer.tags(node)), ">")
        .append(" {");

    if (analyzer.indexOf(node) != 0) {
      codeBuilder
          .append("\n  private final ")
          .append(listenerName)
          .append("<", encode(analyzer.parameters()), ">")
          .append(" listener;\n\n  ")
          .append(analyzer.implModifier(node))
          .append(analyzer.implName(node))
          .append("(")
          .append(listenerName)
          .append("<", encode(analyzer.parameters()), ">")
          .append(" listener")
          .append(")")
          .append("{\n    ")
          .append("this.listener = listener;\n")
          .append("  }\n");
    }

    for (GraphEdge edge : node.edges()) {
      codeBuilder
          .append("\n  @Override\n  public ")
          .append("<", encode(analyzer.tags(edge)), "> ")
          .append(encode(analyzer.destination(edge)))
          .append(" ")
          .append(encode(edge.label().as(Method.class)))
          .append("{\n    ");

      if (analyzer.indexOf(node) == 0) {
        codeBuilder.append(listenerName);
        if (!analyzer.parameters().isEmpty()) {
          List<String> list = new ArrayList<>();
          List<TypeParameter> params = new ArrayList<>(analyzer.parameters());
          List<TypeParameter> knownParams = analyzer.tags(node);
          knownParams.addAll(analyzer.tags(edge));
          for (TypeParameter param : params) {
            if (knownParams.contains(param)) {
              list.add(param.name());
            } else {
              list.add("?");
            }
          }
          codeBuilder.append("<", list, ", ", ">");
        }
        codeBuilder.append(" listener = new ").append(listenerName);
        if (!analyzer.parameters().isEmpty()) {
          codeBuilder.append("<>");
        }
        codeBuilder.append("();\n    ");
      }

      if (analyzer.listenerDestination(edge) != null) {
        if (analyzer.destination(edge) != null) {
          codeBuilder.append("return ");
        }
      }

      codeBuilder
          .append("listener.")
          .append(encodeAsInvocation(edge.label().as(Method.class)))
          .append(";\n");

      if (analyzer.listenerDestination(edge) == null) {
        if (analyzer.destination(edge) != null) {
          codeBuilder.append("    return new ").append(analyzer.implName(edge.destination()));
          if (!edge.destination().tags().isEmpty()) {
            codeBuilder.append("<>");
          }
          codeBuilder.append("(listener);\n");
        }
      }

      codeBuilder.append("  }\n");
    }

    codeBuilder.append("}\n");

    return codeBuilder.generate(path(analyzer.implQualifiedName(node)));
  }

  private GeneratedFile generateListener() {
    String name = "I" + analyzer.name() + "Listener";
    CodeBuilder codeBuilder = new CodeBuilder();

    codeBuilder
        .append("package ", encode(analyzer.packageName()), ";\n\n")
        .append("interface ")
        .append(name)
        .append("<", encode(analyzer.parameters()), ">")
        .append(" {");

    Set<Method> methods = new HashSet<>();
    for (GraphNode node : analyzer.nodes()) {
      for (GraphEdge edge : node.edges()) {
        Method method = edge.label().as(Method.class);
        if (!methods.contains(method)) {
          codeBuilder
              .append("\n  ")
              .append(encode(analyzer.listenerDestination(edge)))
              .append(" ")
              .append(encode(method))
              .append(";\n");
          methods.add(method);
        }
      }
    }

    codeBuilder.append("}\n");

    QualifiedName pkgName = new QualifiedName(analyzer.packageName(), name);
    return codeBuilder.generate(path(pkgName));
  }

  private static Path path(QualifiedName name) {
    return Paths.get(String.join(File.separator, encode(name).split("\\.")) + ".java");
  }

  private static String encode(List<TypeParameter> parameters) {
    return parameters.stream().map(JavaGenerator::encode).collect(Collectors.joining(", "));
  }

  private static String encode(TypeParameter parameter) {
    return parameter.name();
  }

  private static String encode(QualifiedName name) {
    if (name == null) {
      return null;
    }
    String qualifier = encode(name.qualifier());
    String text = name.name();
    return qualifier == null ? text : qualifier + "." + text;
  }

  private static String encodeAsInvocation(Method method) {
    String parameters = encodeAsInvocation(method.parameters());
    return method.name() + "(" + (parameters == null ? "" : parameters) + ")";
  }

  private static String encodeAsInvocation(MethodParameters parameters) {
    if (parameters == null) {
      return null;
    }
    String head = encodeAsInvocation(parameters.head());
    String tail = encodeAsInvocation(parameters.tail());
    return tail == null ? head : head + ", " + tail;
  }

  private static String encodeAsInvocation(MethodParameter parameter) {
    return parameter.name();
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
