package silverchain;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import picocli.CommandLine;
import silverchain.internal.JarProperties;
import silverchain.internal.backend.builder.JavaTranslator;
import silverchain.internal.front.parser.antlr.AgParser.InputContext;
import silverchain.internal.frontend.checker.DuplicateFragmentChecker;
import silverchain.internal.frontend.checker.DuplicateTypeChecker;
import silverchain.internal.frontend.checker.ImportConflictChecker;
import silverchain.internal.frontend.checker.InvalidRangeChecker;
import silverchain.internal.frontend.checker.UndefinedFragmentChecker;
import silverchain.internal.frontend.checker.ZeroRepeatChecker;
import silverchain.internal.frontend.parser.AgParser;
import silverchain.internal.frontend.rewriter.FragmentExpander;
import silverchain.internal.frontend.rewriter.ImportExpander;
import silverchain.internal.middleware.graph.builder.AgCompiler;
import silverchain.internal.middleware.graph.checker.EdgeConflictValidator;
import silverchain.internal.middleware.graph.checker.FileCountChecker;
import silverchain.internal.middleware.graph.data.graph.collection.Graphs;
import silverchain.internal.middleware.graph.rewriter.GraphDeterminizer;
import silverchain.internal.middleware.graph.rewriter.GraphReverser;
import silverchain.internal.middleware.graph.rewriter.ParamPropagator;
import silverchain.internal.middleware.graph.rewriter.ParamRefResolver;
import silverchain.internal.middleware.java.builder.GraphTranslator;
import silverchain.internal.middleware.java.data.CompilationUnits;
import silverchain.internal.middleware.java.rewriter.ActionInterfaceGenerator;
import silverchain.internal.middleware.java.rewriter.JavadocProcessor;
import silverchain.internal.middleware.java.rewriter.NodeClassGenerator;

@CommandLine.Command(name = "silverchain", versionProvider = Silverchain.class, sortOptions = false)
public class Silverchain implements Callable<Integer>, CommandLine.IVersionProvider {

  @SuppressWarnings("unused")
  @CommandLine.Option(
      names = {"-h", "--help"},
      usageHelp = true,
      description = "Show this message and exit")
  private boolean helpRequested;

  @SuppressWarnings("unused")
  @CommandLine.Option(
      names = {"-v", "--version"},
      versionHelp = true,
      description = "Show version and exit")
  private boolean versionRequested;

  @CommandLine.Option(
      names = {"-i", "--input"},
      description = "Input grammar file",
      defaultValue = "-",
      paramLabel = "<path>")
  private String input = "-";

  @CommandLine.Option(
      names = {"-o", "--output"},
      description = "Output directory",
      defaultValue = ".",
      paramLabel = "<path>")
  private String output = ".";

  @CommandLine.Option(
      names = {"-j", "--javadoc"},
      description = "Javadoc source directory",
      paramLabel = "<path>")
  private String javadoc;

  @CommandLine.Option(
      names = {"-m", "--max-file-count"},
      description = "Max number of generated files",
      paramLabel = "<n>",
      defaultValue = "500")
  private int maxFileCount = 500;

  private Consumer<SilverchainWarning> warningHandler = w -> System.err.println(w.message());

  public static void run(String... args) {
    new CommandLine(new Silverchain()).execute(args);
  }

  public void run(InputStream inputStream) throws IOException {
    CharStream charStream = CharStreams.fromStream(inputStream);
    InputContext ctx = new AgParser().parse(charStream);
    ParseTreeWalker.DEFAULT.walk(new DuplicateTypeChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new ZeroRepeatChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new InvalidRangeChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new ImportConflictChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new DuplicateFragmentChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new UndefinedFragmentChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new ImportExpander(), ctx);
    ParseTreeWalker.DEFAULT.walk(new FragmentExpander(), ctx);

    Graphs graphs = new AgCompiler().compile(ctx);
    new GraphReverser().visit(graphs);
    new GraphDeterminizer().visit(graphs);
    new GraphReverser().visit(graphs);
    new GraphDeterminizer().visit(graphs);
    new ParamRefResolver().visit(graphs);
    new ParamPropagator().visit(graphs);
    new FileCountChecker(maxFileCount).visit(graphs);
    new EdgeConflictValidator().visit(graphs);

    CompilationUnits stateInterfaces = new GraphTranslator().translate(graphs);
    new JavadocProcessor(javadoc, warningHandler).process(stateInterfaces);

    CompilationUnits actionInterface = new ActionInterfaceGenerator().generate(stateInterfaces);

    CompilationUnits stateClasses = new NodeClassGenerator().generate(stateInterfaces);

    new JavaTranslator().translate(stateInterfaces, actionInterface, stateClasses).save(output);
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  public String getJavadoc() {
    return javadoc;
  }

  public void setJavadoc(String javadoc) {
    this.javadoc = javadoc;
  }

  public int getMaxFileCount() {
    return maxFileCount;
  }

  public void setMaxFileCount(int maxFileCount) {
    this.maxFileCount = maxFileCount;
  }

  public Consumer<SilverchainWarning> getWarningHandler() {
    return warningHandler;
  }

  public void setWarningHandler(Consumer<SilverchainWarning> warningHandler) {
    this.warningHandler = warningHandler;
  }

  @Override
  public Integer call() throws Exception {
    new Silverchain().run(input.equals("-") ? System.in : new FileInputStream(input));
    return 0;
  }

  @Override
  public String[] getVersion() {
    return new String[] {JarProperties.getProperty("version")};
  }
}
