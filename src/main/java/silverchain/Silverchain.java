package silverchain;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import picocli.CommandLine;
import silverchain.internal.JarProperties;
import silverchain.internal.back.parser.JavaTranslator;
import silverchain.internal.front.TreeWalker;
import silverchain.internal.front.parser.AgParser;
import silverchain.internal.front.parser.antlr.AgParser.InputContext;
import silverchain.internal.front.rewriter.FragmentResolver;
import silverchain.internal.front.rewriter.ImportResolver;
import silverchain.internal.front.validator.DuplicateFragmentValidator;
import silverchain.internal.front.validator.DuplicateTypeDeclValidator;
import silverchain.internal.front.validator.ImportConflictValidator;
import silverchain.internal.front.validator.InvalidRangeValidator;
import silverchain.internal.front.validator.UndefinedFragmentValidator;
import silverchain.internal.front.validator.ZeroRepeatValidator;
import silverchain.internal.middle.graph.data.graph.collection.Graphs;
import silverchain.internal.middle.graph.parser.AgCompiler;
import silverchain.internal.middle.graph.rewriter.GraphDeterminizer;
import silverchain.internal.middle.graph.rewriter.GraphReverser;
import silverchain.internal.middle.graph.rewriter.ParamPropagator;
import silverchain.internal.middle.graph.rewriter.ParamRefResolver;
import silverchain.internal.middle.graph.validator.EdgeConflictValidator;
import silverchain.internal.middle.graph.validator.FileCountChecker;
import silverchain.internal.middle.java.ActionInterfaceGenerator;
import silverchain.internal.middle.java.CompilationUnits;
import silverchain.internal.middle.java.JavadocProcessor;
import silverchain.internal.middle.java.NodeClassGenerator;
import silverchain.internal.middle.java.parser.GraphTranslator;

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
    TreeWalker.walk(new DuplicateTypeDeclValidator(), ctx);
    TreeWalker.walk(new ZeroRepeatValidator(), ctx);
    TreeWalker.walk(new InvalidRangeValidator(), ctx);
    TreeWalker.walk(new ImportConflictValidator(), ctx);
    TreeWalker.walk(new DuplicateFragmentValidator(), ctx);
    TreeWalker.walk(new UndefinedFragmentValidator(), ctx);
    TreeWalker.walk(new ImportResolver(), ctx);
    TreeWalker.walk(new FragmentResolver(), ctx);

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
