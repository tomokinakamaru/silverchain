package silverchain;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.apiguardian.api.API;
import picocli.CommandLine;
import silverchain.ag.AgParser;
import silverchain.ag.AliasConflictChecker;
import silverchain.ag.AliasResolver;
import silverchain.ag.DuplicateFragmentChecker;
import silverchain.ag.DuplicateTypeChecker;
import silverchain.ag.FragmentResolver;
import silverchain.ag.InvalidRangeChecker;
import silverchain.ag.PermutationRewriter;
import silverchain.ag.RangeRewriter;
import silverchain.ag.UndefinedFragmentChecker;
import silverchain.ag.ZeroRepeatChecker;
import silverchain.ag.data.DeclsTree;
import silverchain.ag.walker.TreeWalker;
import silverchain.graph.EdgeConflictChecker;
import silverchain.graph.GraphBuilder;
import silverchain.graph.GraphDeterminizer;
import silverchain.graph.GraphReverser;
import silverchain.graph.ParamPropagator;
import silverchain.graph.TypeRefResolver;
import silverchain.graph.data.Graphs;
import silverchain.graph.walker.GraphWalker;
import silverchain.java.JavaGenerator;
import silverchain.java.NodeAnalyzer;
import silverchain.java.NodeBuilder;
import silverchain.java.data.TypesNode;

@API(status = API.Status.EXPERIMENTAL)
@CommandLine.Command(name = "silverchain", versionProvider = Silverchain.class, sortOptions = false)
public class Silverchain implements Callable<Integer>, CommandLine.IVersionProvider {

  @CommandLine.Option(
      names = {"-h", "--help"},
      usageHelp = true,
      description = "Show this message and exit")
  private boolean helpRequested;

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

  private Consumer<SilverchainWarning> warningHandler = w -> System.err.println(w.getMessage());

  public static void main(String... args) {
    new CommandLine(new Silverchain()).execute(args);
  }

  public void run(String text) {
    DeclsTree tree = new AgParser().parse(text);
    TreeWalker.walk(tree, new AliasConflictChecker());
    TreeWalker.walk(tree, new DuplicateTypeChecker());
    TreeWalker.walk(tree, new DuplicateFragmentChecker());
    TreeWalker.walk(tree, new UndefinedFragmentChecker());
    TreeWalker.walk(tree, new InvalidRangeChecker());
    TreeWalker.walk(tree, new ZeroRepeatChecker());
    TreeWalker.walk(tree, new AliasResolver());
    TreeWalker.walk(tree, new FragmentResolver());
    TreeWalker.walk(tree, new RangeRewriter());
    TreeWalker.walk(tree, new PermutationRewriter());

    Graphs graphs = GraphBuilder.build(tree);
    GraphWalker.walk(graphs, new GraphReverser());
    GraphWalker.walk(graphs, new GraphDeterminizer());
    GraphWalker.walk(graphs, new GraphReverser());
    GraphWalker.walk(graphs, new GraphDeterminizer());
    GraphWalker.walk(graphs, new TypeRefResolver());
    GraphWalker.walk(graphs, new ParamPropagator());
    GraphWalker.walk(graphs, new EdgeConflictChecker());

    TypesNode node = NodeBuilder.build(graphs);
    new NodeAnalyzer().analyze(node);

    new JavaGenerator(output).save(node);
  }

  @Override
  public Integer call() throws Exception {
    InputStream stream = input.equals("-") ? System.in : new FileInputStream(input);
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    run(reader.lines().collect(Collectors.joining("\n")));
    return 0;
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
  public String[] getVersion() throws IOException {
    Properties properties = new Properties();
    properties.load(Properties.class.getResourceAsStream("silverchain.properties"));
    return new String[] {properties.getProperty("version")};
  }
}
