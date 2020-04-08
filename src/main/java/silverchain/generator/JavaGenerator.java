package silverchain.generator;

import static silverchain.generator.JavaASTEncoder.encodeAsDeclaration;
import static silverchain.generator.JavaDiagramEncoder.actionMethodDeclaration;
import static silverchain.generator.JavaDiagramEncoder.actionMethodDefaultBody;
import static silverchain.generator.JavaDiagramEncoder.implementationName;
import static silverchain.generator.JavaDiagramEncoder.implementationPackageName;
import static silverchain.generator.JavaDiagramEncoder.implementationQualifiedName;
import static silverchain.generator.JavaDiagramEncoder.interfaceModifier;
import static silverchain.generator.JavaDiagramEncoder.interfaceName;
import static silverchain.generator.JavaDiagramEncoder.interfacePackageName;
import static silverchain.generator.JavaDiagramEncoder.interfaceQualifiedName;
import static silverchain.generator.JavaDiagramEncoder.interfaceReference;
import static silverchain.generator.JavaDiagramEncoder.stateMethodBodyListenerInvocation;
import static silverchain.generator.JavaDiagramEncoder.stateMethodBodyReturnState;
import static silverchain.generator.JavaDiagramEncoder.stateMethodDeclaration;
import static silverchain.generator.JavaValidator.validate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import silverchain.diagram.Diagram;
import silverchain.diagram.State;
import silverchain.diagram.Transition;
import silverchain.parser.Method;

public final class JavaGenerator extends Generator {

  public JavaGenerator(List<Diagram> diagrams) {
    super(diagrams);
  }

  @Override
  void generate(List<Diagram> diagrams) {
    diagrams.forEach(this::generate);
  }

  private void generate(Diagram diagram) {
    diagram.assignStateNumbers(s -> !s.isEnd());
    validate(diagram);
    generateInterface(diagram);
    for (State state : diagram.numberedStates()) {
      generateInterface(state);
      generateImplementation(state);
    }
  }

  private void generateInterface(State state) {
    beginFile(filePath(interfaceQualifiedName(state)));
    write(packageDeclaration(interfacePackageName(state)));

    write(interfaceModifier(state));
    write("interface ");
    write(interfaceName(state));
    write(encodeAsDeclaration(state.typeParameters()));
    write(" {\n");

    for (Transition transition : state.transitions()) {
      write("\n  ");
      write(stateMethodDeclaration(transition));
      write(";\n");
    }

    write("}\n");
    endFile();
  }

  private void generateImplementation(State state) {
    beginFile(filePath(implementationQualifiedName(state)));
    write(packageDeclaration(implementationPackageName(state)));

    write("@SuppressWarnings({\"rawtypes\", \"unchecked\"})\nclass ");
    write(implementationName(state));
    write(encodeAsDeclaration(state.typeParameters()));
    write(" implements ");
    write(interfaceReference(state));
    write(" {\n\n  ");

    write(interfaceQualifiedName(state.diagram()));
    write(" action;\n\n  ");

    write(implementationName(state));
    write("(");
    write(interfaceQualifiedName(state.diagram()));
    write(" action) {\n    this.action = action;\n  }\n");

    for (Transition transition : state.transitions()) {
      write("\n  @Override\n  public ");
      write(stateMethodDeclaration(transition));
      write(" {\n");
      write(stateMethodBodyListenerInvocation(transition));
      write(stateMethodBodyReturnState(transition));
      write("  }\n");
    }

    write("}\n");
    endFile();
  }

  private void generateInterface(Diagram diagram) {
    beginFile(filePath(interfaceQualifiedName(diagram)));
    write(packageDeclaration(interfacePackageName(diagram)));
    write("interface ");
    write(interfaceName(diagram));
    write(encodeAsDeclaration(diagram.typeParameters()));
    write(" {\n");
    interfaceDefaultMethods(diagram);
    interfaceMethods(diagram);
    write("}\n");
    endFile();
  }

  private void interfaceDefaultMethods(Diagram diagram) {
    for (State state : diagram.numberedStates()) {
      for (Transition transition : state.transitions()) {
        write("\n  default ");
        write(actionMethodDeclaration(transition, true));
        write(" {\n    ");
        write(actionMethodDefaultBody(transition));
        write("\n  }");
        write("\n");
      }
    }
  }

  private void interfaceMethods(Diagram diagram) {
    Set<Method> encodedMethods = new HashSet<>();
    for (State state : diagram.numberedStates()) {
      for (Transition transition : state.transitions()) {
        if (!encodedMethods.contains(transition.label().method())) {
          write("\n  ");
          write(actionMethodDeclaration(transition, false));
          write(";\n");
          encodedMethods.add(transition.label().method());
        }
      }
    }
  }

  private String filePath(String name) {
    return name.replaceAll("\\.", "/") + ".java";
  }

  private String packageDeclaration(String name) {
    return name.isEmpty() ? "" : "package " + name + ";\n\n";
  }
}
