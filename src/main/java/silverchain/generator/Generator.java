package silverchain.generator;

import static java.lang.String.join;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;
import silverchain.diagram.Diagram;
import silverchain.diagram.Diagrams;
import silverchain.diagram.Label;
import silverchain.diagram.State;
import silverchain.diagram.Transition;
import silverchain.javadoc.Javadocs;
import silverchain.parser.FormalParameter;
import silverchain.parser.FormalParameters;
import silverchain.parser.Method;
import silverchain.parser.MethodParameters;
import silverchain.parser.QualifiedName;
import silverchain.parser.TypeArgument;
import silverchain.parser.TypeArguments;
import silverchain.parser.TypeParameter;
import silverchain.parser.TypeParameterBound;
import silverchain.parser.TypeParameterList;
import silverchain.parser.TypeReference;
import silverchain.parser.TypeReferences;

public final class Generator {

  private final Diagrams diagrams;

  private final Javadocs javadocs;

  private final List<File> files;

  private Path path;

  private StringBuilder stringBuilder;

  public Generator(Diagrams diagrams, Javadocs javadocs) {
    this.diagrams = diagrams;
    this.javadocs = javadocs;
    this.files = new ArrayList<>();
  }

  public List<File> generate() {
    javadocs.init();
    generate(new Diagrams(diagrams), javadocs);
    return files;
  }

  private void generate(Diagrams diagrams, Javadocs javadocs) {
    diagrams.forEach(diagram -> diagram.assignStateNumbers(s -> !s.isEnd()));
    diagrams.forEach(diagram -> generate(diagram, javadocs));
  }

  private void generate(Diagram diagram, Javadocs javadocs) {
    generateIAction(diagram);
    diagram.numberedStates().forEach(state -> generate(state, javadocs));
  }

  private void generate(State state, Javadocs javadocs) {
    generateIState(state, javadocs);
    generateState(state);
  }

  private void generateIState(State state, Javadocs javadocs) {
    beginFile(getFilePath(getIStateQualifiedName(state)));
    writePackageDeclaration(getIStatePackageName(state));

    // Interface declaration
    write(getIStateModifier(state));
    writeInterfaceDeclaration(getIStateName(state));
    writeTypeParameterDeclaration(state.typeParameters());
    writeLeftBracket();

    // Method declaration
    for (Transition transition : state.transitions()) {
      writeLineBreak();
      writeIndentation();
      pasteComment(transition, javadocs);
      writeStateMethodDeclaration(transition);
      writeSemicolon();
    }

    writeRightBracket();
    endFile();
  }

  private void pasteComment(Transition transition, Javadocs javadocs) {
    String pkg = getIActionPackageName(transition.source().diagram());
    String cls = getIActionName(transition.source().diagram());
    int state = transition.source().number();
    Method method = transition.label().method();
    String comment = javadocs.get(pkg, cls, state, method);
    if (comment != null) {
      write(comment);
      writeLineBreak();
      writeIndentation();
    }
  }

  private void generateState(State state) {
    beginFile(getFilePath(getStateQualifiedName(state)));
    writePackageDeclaration(getStatePackageName(state));

    // Class declaration
    write("@SuppressWarnings({\"rawtypes\", \"unchecked\"})");
    writeLineBreak();
    writeClassDeclarationHead(getStateName(state));
    writeTypeParameterDeclaration(state.typeParameters());
    writeInterface(getIStateReference(state));
    writeLeftBracket();
    writeLineBreak();

    // Field declaration
    writeIndentation();
    writeActionDeclaration(state.diagram());
    writeSemicolon();
    writeLineBreak();

    // Constructor
    writeIndentation();
    write(getStateName(state));
    write("(");
    writeActionDeclaration(state.diagram());
    write(")");
    writeLeftBracket();
    writeIndentation();
    writeIndentation();
    write("this.action = action");
    writeSemicolon();
    writeIndentation();
    writeRightBracket();

    // Method declaration
    for (Transition transition : state.transitions()) {
      writeLineBreak();
      writeIndentation();
      write("@Override");
      writeLineBreak();
      writeIndentation();
      write("public ");
      write(getStateMethodHead(transition));
      writeLeftBracket();
      write(getStateMethodBodyListenerInvocation(transition));
      write(getStateMethodBodyReturnNextState(transition));
      writeIndentation();
      writeRightBracket();
    }

    writeRightBracket();
    endFile();
  }

  private void generateIAction(Diagram diagram) {
    beginFile(getFilePath(getIActionQualifiedName(diagram)));
    writePackageDeclaration(getIActionPackageName(diagram));

    // Interface declaration
    writeInterfaceDeclaration(getIActionName(diagram));
    writeTypeParameterDeclaration(diagram.typeParameters());
    writeLeftBracket();

    List<Transition> transitions =
        diagram.numberedStates().stream()
            .map(State::transitions)
            .flatMap(Collection::stream)
            .collect(toList());

    for (Transition transition : transitions) {
      writeLineBreak();
      writeIndentation();
      write("default ");
      write(getIActionMethodHead(transition, true));
      writeLeftBracket();
      writeIndentation();
      writeIndentation();
      write(getIActionMethodBody(transition));
      writeLineBreak();
      writeIndentation();
      writeRightBracket();
    }

    Set<Method> encodedMethods = new HashSet<>();
    for (Transition transition : transitions) {
      if (!encodedMethods.contains(transition.label().method())) {
        writeLineBreak();
        writeIndentation();
        write(getIActionMethodHead(transition, false));
        writeSemicolon();
        encodedMethods.add(transition.label().method());
      }
    }

    writeRightBracket();
    endFile();
  }

  private void writePackageDeclaration(String name) {
    write(name.isEmpty() ? "" : "package " + name + ";\n\n");
  }

  private void writeClassDeclarationHead(String name) {
    write("class ");
    write(name);
  }

  private void writeInterfaceDeclaration(String name) {
    write("interface ");
    write(name);
  }

  private void writeTypeParameterDeclaration(List<TypeParameter> parameters) {
    write(encode(parameters, true));
  }

  private void writeInterface(String name) {
    write(" implements ");
    write(name);
  }

  private void writeActionDeclaration(Diagram diagram) {
    write(getIActionQualifiedName(diagram));
    write(" action");
  }

  private void writeLeftBracket() {
    write(" {\n");
  }

  private void writeRightBracket() {
    write("}\n");
  }

  private void writeSemicolon() {
    write(";\n");
  }

  private void writeLineBreak() {
    write("\n");
  }

  private void writeIndentation() {
    write("  ");
  }

  private void writeStateMethodDeclaration(Transition transition) {
    write(getStateMethodHead(transition));
  }

  private String getFilePath(String name) {
    return name.replace('.', '/') + ".java";
  }

  /*
   * For diagram encoding
   */
  private String getStateMethodHead(Transition transition) {
    List<TypeParameter> p = transition.typeParameters();
    State d = transition.destination();
    Label l = transition.label();
    String s1 = p.isEmpty() ? "" : encode(p, true) + " ";
    String s2 = getIStateReference(d) + " " + encode(l.method(), true);
    return s1 + s2;
  }

  private String getStateMethodBodyListenerInvocation(Transition transition) {
    Optional<Label> r = transition.destination().typeReference();
    String s1 = r.map(l -> "return ").orElse("");
    String s2 = r.map(l -> l.typeReference().referent()).map(l -> "(" + l.name() + ") ").orElse("");
    return "    " + s1 + s2 + "this.action." + getIActionMethodInvocation(transition, true) + ";\n";
  }

  private String getStateMethodBodyReturnNextState(Transition transition) {
    State d = transition.destination();
    return d.isNumbered() ? "    return new " + getStateQualifiedName(d) + "(this.action);\n" : "";
  }

  private String getIActionMethodHead(Transition transition, boolean full) {
    MethodParameters mps = transition.label().method().parameters();
    Optional<TypeParameterList> opt = mps.localTypeParameters();
    List<TypeParameter> lst = opt.map(l -> l.stream().collect(toList())).orElse(emptyList());
    String s1 = lst.isEmpty() ? "" : encode(lst, true) + " ";

    State d = transition.destination();
    String s2 = d.isNumbered() ? "void" : getIStateReference(d);
    return s1 + s2 + " " + getIActionMethodSignature(transition, true, full);
  }

  private String getIActionMethodBody(Transition transition) {
    String s = transition.destination().typeReference().map(r -> "return ").orElse("");
    return s + getIActionMethodInvocation(transition, false) + ";";
  }

  private String getIActionMethodInvocation(Transition transition, boolean full) {
    return getIActionMethodSignature(transition, false, full);
  }

  private String getIActionMethodSignature(Transition transition, boolean decl, boolean full) {
    String prefix = full ? ("state" + transition.source().number() + "$") : "";
    return prefix + encode(transition.label().method(), decl);
  }

  private String getIStateModifier(State state) {
    return "public ";
  }

  private String getIStateQualifiedName(State state) {
    return qualifyName(getIStatePackageName(state), getIStateName(state));
  }

  private String getIStatePackageName(State state) {
    String qualifier = state.diagram().name().qualifier().map(this::encode).orElse("");
    return qualifyName(qualifier, "intermediates");
  }

  private String getIStateName(State state) {
    return state.diagram().name().name() + state.number();
  }

  private String getIStateReference(State state) {
    if (state.isNumbered()) {
      return getIStateQualifiedName(state) + encode(state.typeParameters(), false);
    }
    return state.typeReference().map(l -> encode(l.typeReference())).orElse("void");
  }

  private String getStateQualifiedName(State state) {
    return qualifyName(getStatePackageName(state), getStateName(state));
  }

  private String getStatePackageName(State state) {
    return state.diagram().name().qualifier().map(this::encode).orElse("");
  }

  private String getStateName(State state) {
    return state.diagram().name().name() + state.number() + "Impl";
  }

  private String getIActionQualifiedName(Diagram diagram) {
    return qualifyName(getIActionPackageName(diagram), getIActionName(diagram));
  }

  private String getIActionPackageName(Diagram diagram) {
    return diagram.name().qualifier().map(this::encode).orElse("");
  }

  private String getIActionName(Diagram diagram) {
    return diagram.name().name() + "Action";
  }

  private String qualifyName(String qualifier, String name) {
    return qualifier.isEmpty() ? name : qualifier + "." + name;
  }

  /*
   * For AST node encoding
   */
  private String encode(List<TypeParameter> parameters, boolean decl) {
    return parameters.isEmpty() ? "" : "<" + csv(parameters.stream(), p -> encode(p, decl)) + ">";
  }

  private String encode(TypeParameter parameter, boolean decl) {
    String bound = decl ? parameter.bound().map(this::encode).orElse("") : "";
    return parameter.name() + bound;
  }

  private String encode(TypeParameterBound bound) {
    String s = bound.isUpper() ? "extends" : "super";
    return " " + s + " " + encode(bound.reference());
  }

  private String encode(Method method, boolean decl) {
    String s = method.parameters().formalParameters().map(p -> encode(p, decl)).orElse("");
    s = method.name() + "(" + s + ")";
    if (decl) {
      s = s + method.exceptions().map(es -> " throws " + encode(es)).orElse("");
    }
    return s;
  }

  private String encode(FormalParameters parameters, boolean decl) {
    return csv(parameters.stream(), p -> encode(p, decl));
  }

  private String encode(FormalParameter parameter, boolean decl) {
    if (decl) {
      String s1 = encode(parameter.type());
      String s2 = parameter.isVarArgs() ? "... " : " ";
      String s3 = parameter.name();
      return s1 + s2 + s3;
    }
    return parameter.name();
  }

  private String encode(TypeReference reference) {
    String s1 = encode(reference.name());
    String s2 = reference.arguments().map(this::encode).orElse("");
    String s3 = reference.isArray() ? "[]" : "";
    return s1 + s2 + s3;
  }

  private String encode(TypeReferences references) {
    return references.stream().map(this::encode).collect(joining(", "));
  }

  private String encode(TypeArgument argument) {
    if (argument.reference().isPresent()) {
      return encode(argument.reference().get());
    }
    return "?" + argument.bound().map(this::encode).orElse("");
  }

  private String encode(TypeArguments arguments) {
    return "<" + csv(arguments.stream(), this::encode) + ">";
  }

  private String encode(QualifiedName name) {
    return join(".", name);
  }

  private <T> String csv(Stream<T> stream, Function<T, String> function) {
    return stream.map(function).collect(joining(", "));
  }

  private void beginFile(String name) {
    path = Paths.get(name);
    stringBuilder = new StringBuilder();
  }

  private void write(String s) {
    stringBuilder.append(s);
  }

  private void endFile() {
    files.add(new File(path, stringBuilder.toString()));
  }
}
