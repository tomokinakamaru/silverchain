package silverchain.codegen.java;

import static silverchain.codegen.java.ASTEncoder.encode;

import java.util.List;
import java.util.stream.Collectors;
import silverchain.codegen.GeneratedFile;
import silverchain.graph.GraphNode;

public final class JavaGenerator {

  private final GraphAdapter graph;

  public JavaGenerator(List<GraphNode> nodes) {
    this.graph = new GraphAdapter(nodes);
  }

  public List<GeneratedFile> generate() {
    return graph
        .nodes()
        .stream()
        .map(n -> new GeneratedFile(n.path(), generate(n)))
        .collect(Collectors.toList());
  }

  private String generate(GraphNodeAdapter node) {
    CodeBuilder codeBuilder = new CodeBuilder();

    codeBuilder
        .append("package ", node.packageName(), ";\n\n")
        .append("public interface ")
        .append(node.name())
        .append("<", node.tags(), ", ", ">")
        .append(" extends ", node.typeReferences(), ", ", null)
        .append(" {");

    for (GraphEdgeAdapter edge : node.edges()) {
      codeBuilder
          .append("\n\n")
          .append("  ")
          .append("<", edge.tags(), ",", "> ")
          .append(edge.destination().qualifiedName())
          .append("<", edge.destination().tags(), ", ", ">")
          .append(" ")
          .append(encode(edge.label()))
          .append(";");
    }

    return codeBuilder.append("\n}").toString();
  }
}
