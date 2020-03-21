package utility;

import java.util.List;
import silverchain.analyzer.Analyzer;
import silverchain.graph.GraphNode;

public class ResourceAnalyzer extends ResourceParser {

  public ResourceAnalyzer(String... names) {
    super(names);
  }

  public List<GraphNode> analyze(String fileName) {
    Analyzer analyzer = new Analyzer(parse(fileName));
    return analyzer.graph().compile(analyzer.option());
  }
}
