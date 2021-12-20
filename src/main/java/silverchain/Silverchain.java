package silverchain;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apiguardian.api.API;
import picocli.CommandLine;
import silverchain.data.graph.Graphs;
import silverchain.data.graph.visitor.GraphWalker;
import silverchain.data.java.CompilationUnits;
import silverchain.process.ag.antlr.AgParser.InputContext;
import silverchain.process.ag.builder.AgParser;
import silverchain.process.ag.builder.AgTreeBuilder;
import silverchain.process.ag.checker.DuplicateFragmentChecker;
import silverchain.process.ag.checker.DuplicateTypeChecker;
import silverchain.process.ag.checker.ImportConflictChecker;
import silverchain.process.ag.checker.InvalidRepeatChecker;
import silverchain.process.ag.checker.UndefinedFragmentChecker;
import silverchain.process.ag.checker.ZeroRepeatChecker;
import silverchain.process.ag.rewriter.FragmentResolver;
import silverchain.process.ag.rewriter.ImportResolver;
import silverchain.process.ag.rewriter.PermutationRewriter;
import silverchain.process.ag.rewriter.RepeatRewriter;
import silverchain.process.graph.builder.GraphBuilder;
import silverchain.process.graph.checker.EdgeConflictValidator;
import silverchain.process.graph.rewriter.GraphDeterminizer;
import silverchain.process.graph.rewriter.GraphReverser;
import silverchain.process.graph.rewriter.ParamPropagator;
import silverchain.process.graph.rewriter.ParamRefResolver;
import silverchain.process.java.Backend;
import silverchain.process.java.JavaMiddleware;

@API(status = API.Status.EXPERIMENTAL)
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

  public void run(CharStream stream) {
    InputContext ctx = rewriteAgTree(checkAgTree(buildAgTree(parse(stream))));
    Graphs graphs = checkGraphs(rewriteGraphs(buildGraphs(ctx)));
    CompilationUnits units = new JavaMiddleware(javadoc, warningHandler).run(graphs);
    new Backend(maxFileCount, output).run(units);
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
    InputStream stream = input.equals("-") ? System.in : new FileInputStream(input);
    new Silverchain().run(CharStreams.fromStream(stream));
    return 0;
  }

  @Override
  public String[] getVersion() {
    return new String[] {SilverchainProperties.getProperty("version")};
  }

  private InputContext parse(CharStream stream) {
    return new AgParser().parse(stream);
  }

  private InputContext buildAgTree(InputContext ctx) {
    return (InputContext) ctx.accept(new AgTreeBuilder());
  }

  private InputContext checkAgTree(InputContext ctx) {
    ParseTreeWalker.DEFAULT.walk(new ImportConflictChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new DuplicateTypeChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new DuplicateFragmentChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new UndefinedFragmentChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new ZeroRepeatChecker(), ctx);
    ParseTreeWalker.DEFAULT.walk(new InvalidRepeatChecker(), ctx);
    return ctx;
  }

  private InputContext rewriteAgTree(InputContext ctx) {
    return (InputContext)
        ctx.accept(new ImportResolver())
            .accept(new FragmentResolver())
            .accept(new RepeatRewriter())
            .accept(new PermutationRewriter());
  }

  private Graphs buildGraphs(InputContext ctx) {
    return ctx.typeDecl().stream()
        .map(d -> d.accept(new GraphBuilder()))
        .collect(Collectors.toCollection(Graphs::new));
  }

  private Graphs rewriteGraphs(Graphs graphs) {
    GraphWalker walker = new GraphWalker();
    walker.walk(new GraphReverser(), graphs);
    walker.walk(new GraphDeterminizer(), graphs);
    walker.walk(new GraphReverser(), graphs);
    walker.walk(new GraphDeterminizer(), graphs);
    walker.walk(new ParamRefResolver(), graphs);
    walker.walk(new ParamPropagator(), graphs);
    return graphs;
  }

  private Graphs checkGraphs(Graphs graphs) {
    GraphWalker walker = new GraphWalker();
    walker.walk(new EdgeConflictValidator(), graphs);
    return graphs;
  }
}
