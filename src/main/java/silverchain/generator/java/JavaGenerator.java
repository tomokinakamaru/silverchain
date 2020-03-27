package silverchain.generator.java;

import static silverchain.generator.java.GrammarEncoder.encode;
import static silverchain.generator.java.Utility.filePath;
import static silverchain.generator.java.Utility.packageDeclaration;

import java.util.List;
import silverchain.generator.Generator;
import silverchain.graph.GraphNode;

public final class JavaGenerator extends Generator {

  public JavaGenerator(List<GraphNode> nodes) {
    super(nodes);
  }

  protected void generate(List<GraphNode> nodes) {
    JavaDiagram diagram = new JavaDiagramBuilder().build(nodes);
    List<JavaState> states = diagram.numberedStates();
    states.forEach(this::generateStateInterface);
    states.forEach(s -> generateStateClass(diagram, s));
    generateActionInterface(diagram);
  }

  private void generateStateInterface(JavaState state) {
    beginFile(filePath(state.interfaceQualifiedName()));
    write(packageDeclaration(state.interfacePackageName()));

    write(state.interfaceModifier());
    write("interface ");
    write(state.interfaceName());
    write(encode(state.parameters()));
    write(" {\n");

    for (JavaTransition transition : state.transitions()) {
      write("\n  ");
      write(transition.stateMethodDeclaration());
      write(";\n");
    }

    write("}\n");
    endFile();
  }

  private void generateStateClass(JavaDiagram diagram, JavaState state) {
    beginFile(filePath(state.implementationQualifiedName()));
    write(packageDeclaration(state.implementationPackageName()));

    write("@SuppressWarnings({\"rawtypes\", \"unchecked\"})\nclass ");
    write(state.implementationName());
    write(encode(state.parameters()));
    write(" implements ");
    write(state.reference());
    write(" {\n\n  ");

    write(diagram.actionInterfaceQualifiedName());
    write(" action;\n\n  ");

    write(state.implementationName());
    write("(");
    write(diagram.actionInterfaceQualifiedName());
    write(" action) {\n    this.action = action;\n  }\n");

    for (JavaTransition transition : state.transitions()) {
      write("\n  @Override\n  public ");
      write(transition.stateMethodDeclaration());
      write(" {\n");
      write(transition.stateMethodBodyListenerInvocation());
      write(transition.stateMethodBodyReturnState());
      write("  }\n");
    }

    write("}\n");
    endFile();
  }

  private void generateActionInterface(JavaDiagram diagram) {
    beginFile(filePath(diagram.actionInterfaceQualifiedName()));
    write(packageDeclaration(diagram.actionInterfacePackageName()));

    write("interface ");
    write(diagram.actionInterfaceName());
    write(encode(diagram.parameters()));
    write(" {\n");

    for (JavaState state : diagram.numberedStates()) {
      for (JavaTransition transition : state.transitions()) {
        write("\n  ");
        write(transition.actionMethodDeclaration());
        write(";\n");
      }
    }

    write("}\n");
    endFile();
  }
}
