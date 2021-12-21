package silverchain;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apiguardian.api.API;
import picocli.CommandLine;
import silverchain.ag.antlr.AgListener;
import silverchain.ag.antlr.AgParser.InputContext;
import silverchain.ag.builder.AgBuilder;
import silverchain.ag.builder.AgLexer;
import silverchain.ag.builder.AgParser;
import silverchain.ag.checker.AliasConflictChecker;
import silverchain.ag.checker.DuplicateFragmentChecker;
import silverchain.ag.checker.DuplicateTypeChecker;
import silverchain.ag.checker.InvalidRepeatChecker;
import silverchain.ag.checker.UndefinedFragmentChecker;
import silverchain.ag.checker.ZeroRepeatChecker;
import silverchain.ag.rewriter.AliasResolver;
import silverchain.ag.rewriter.FragmentResolver;
import silverchain.ag.rewriter.PermutationRewriter;
import silverchain.ag.rewriter.RepeatRewriter;
import silverchain.data.graph.Graphs;
import silverchain.data.graph.visitor.GraphWalker;
import silverchain.data.java.JavaFiles;
import silverchain.process.graph.builder.GraphBuilder;
import silverchain.process.graph.checker.EdgeConflictValidator;
import silverchain.process.graph.rewriter.GraphDeterminizer;
import silverchain.process.graph.rewriter.GraphReverser;
import silverchain.process.graph.rewriter.ParamPropagator;
import silverchain.process.graph.rewriter.ParamRefResolver;

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
    InputContext ctx = analyze(parse(stream));
    Graphs graphs = check(rewrite(build(ctx)));
    JavaFiles files = check(rewrite(build(graphs)));
    files.save(output);
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

  protected InputContext parse(CharStream stream) {
    return parser().apply(stream);
  }

  protected InputContext analyze(InputContext ctx) {
    for (AgListener listener : analyzers()) ParseTreeWalker.DEFAULT.walk(listener, ctx);
    return ctx;
  }

  protected Function<CharStream, InputContext> parser() {
    return stream -> new AgParser(new CommonTokenStream(new AgLexer(stream))).input();
  }

  protected List<AgListener> analyzers() {
    return Arrays.asList(
        new AgBuilder(),
        new AliasConflictChecker(),
        new DuplicateTypeChecker(),
        new DuplicateFragmentChecker(),
        new UndefinedFragmentChecker(),
        new ZeroRepeatChecker(),
        new InvalidRepeatChecker(),
        new AliasResolver(),
        new FragmentResolver(),
        new RepeatRewriter(),
        new PermutationRewriter());
  }

  private Graphs build(InputContext ctx) {
    return ctx.typeDecl().stream()
        .map(d -> d.accept(new GraphBuilder()))
        .collect(Collectors.toCollection(Graphs::new));
  }

  private Graphs rewrite(Graphs graphs) {
    GraphWalker walker = new GraphWalker();
    walker.walk(new GraphReverser(), graphs);
    walker.walk(new GraphDeterminizer(), graphs);
    walker.walk(new GraphReverser(), graphs);
    walker.walk(new GraphDeterminizer(), graphs);
    walker.walk(new ParamRefResolver(), graphs);
    walker.walk(new ParamPropagator(), graphs);
    return graphs;
  }

  private Graphs check(Graphs graphs) {
    GraphWalker walker = new GraphWalker();
    walker.walk(new EdgeConflictValidator(), graphs);
    return graphs;
  }

  private JavaFiles build(Graphs graphs) {
    return new JavaFiles();
  }

  private JavaFiles rewrite(JavaFiles files) {
    return files;
  }

  private JavaFiles check(JavaFiles files) {
    return files;
  }
}
