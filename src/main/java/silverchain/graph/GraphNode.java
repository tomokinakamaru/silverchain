package silverchain.graph;

import java.util.ArrayList;
import java.util.List;

public final class GraphNode {

  GraphTags tags;

  List<GraphEdge> sortedEdges;

  boolean isStart;

  boolean isEnd;

  GraphNode() {}

  public List<GraphTag> tags() {
    return new ArrayList<>(tags);
  }

  public List<GraphEdge> edges() {
    return new ArrayList<>(sortedEdges);
  }

  public boolean isStart() {
    return isStart;
  }

  public boolean isEnd() {
    return isEnd;
  }
}
