package silverchain.codegen.java;

import static silverchain.codegen.java.Utility.encodeQualifiedName;
import static silverchain.codegen.java.Utility.encodeTypeReference;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import silverchain.codegen.GeneratedFile;
import silverchain.grammar.Method;
import silverchain.grammar.MethodParameters;
import silverchain.grammar.QualifiedName;
import silverchain.grammar.Type;
import silverchain.grammar.TypeReference;
import silverchain.graph.GraphEdge;
import silverchain.graph.GraphNode;

public final class JavaGenerator {

  private final List<GraphNode> nodes;

  private StringBuilder stringBuilder;

  public JavaGenerator(List<GraphNode> nodes) {
    this.nodes = nodes;
  }

  public List<GeneratedFile> generate() {
    List<GeneratedFile> files = new ArrayList<>();
    for (GraphNode node : nodes) {
      if (!node.isStart() && !node.isEnd()) {
        stringBuilder = new StringBuilder();
        generate(node);
        files.add(new GeneratedFile(getPath(node), stringBuilder.toString()));
      }
    }
    return files;
  }

  private void generate(GraphNode node) {
    // package
    String pkg = getPackageName(node);
    if (pkg != null) {
      stringBuilder.append("package ").append(pkg).append(";\n\n");
    }

    // interface declaration
    stringBuilder.append("public interface ").append(getName(node));

    // type parameters
    if (!node.tags().isEmpty()) {
      stringBuilder.append("<").append(String.join(", ", node.tags())).append(">");
    }

    // super interfaces
    List<String> refs = new ArrayList<>();
    for (GraphEdge edge : node.edges()) {
      if (edge.label().is(TypeReference.class)) {
        refs.add(encodeTypeReference(edge.label().as(TypeReference.class)));
      }
    }
    if (!refs.isEmpty()) {
      stringBuilder.append(" extends ").append(String.join(", ", refs));
    }

    // interface declaration body
    boolean methodGenerated = false;
    stringBuilder.append(" {");
    for (GraphEdge edge : node.edges()) {
      if (edge.label().is(Method.class)) {
        generate(edge);
        methodGenerated = true;
      }
    }
    stringBuilder.append(methodGenerated ? "\n}" : "}");
  }

  private void generate(GraphEdge edge) {
    // indent
    stringBuilder.append("\n\n").append("  ");

    // type parameters
    List<String> tags = new ArrayList<>(edge.destination().tags());
    tags.removeAll(edge.source().tags());
    if (!tags.isEmpty()) {
      stringBuilder.append("<").append(String.join(", ", tags)).append("> ");
    }

    // return type
    stringBuilder.append(getQualifiedName(edge.destination()));
    if (!edge.destination().tags().isEmpty()) {
      stringBuilder.append("<").append(String.join(", ", edge.destination().tags())).append(">");
    }
    stringBuilder.append(" ");

    // name
    stringBuilder.append(edge.label().as(Method.class).name());

    // method parameters
    stringBuilder.append("(");
    generate(edge.label().as(Method.class).parameters());
    stringBuilder.append(");");
  }

  private void generate(MethodParameters parameters) {
    while (parameters != null) {
      String type = encodeTypeReference(parameters.head().type());
      String name = parameters.head().name();
      stringBuilder.append(type).append(" ").append(name);
      parameters = parameters.tail();
      if (parameters != null) {
        stringBuilder.append(", ");
      }
    }
  }

  private Path getPath(GraphNode node) {
    return Paths.get(getQualifiedName(node).replaceAll("\\.", File.separator) + ".java");
  }

  private String getQualifiedName(GraphNode node) {
    String s = getPackageName(node);
    String t = getName(node);
    return s == null ? t : s + "." + t;
  }

  private String getPackageName(GraphNode node) {
    QualifiedName qualifier = getType().name().qualifier();
    int number = getNumber(node);
    if (qualifier == null) {
      if (number == 0) {
        return null;
      }
      return "state" + number;
    }
    String s = encodeQualifiedName(qualifier);
    if (number == 0) {
      return s;
    }
    return s + ".state" + number;
  }

  private String getName(GraphNode node) {
    return getType().name().name();
  }

  private Type getType() {
    return nodes.get(0).edges().get(0).label().as(Type.class);
  }

  private int getNumber(GraphNode node) {
    List<GraphNode> ns = new ArrayList<>();
    for (GraphNode n : nodes) {
      if (!n.isStart() && !n.isEnd()) {
        ns.add(n);
      }
    }
    return ns.indexOf(node);
  }
}
