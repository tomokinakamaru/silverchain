package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import silverchain.graph.GraphEdge;
import silverchain.graph.GraphNode;

public final class GraphTester {

  private final List<GraphNode> nodes;

  public GraphTester(List<GraphNode> nodes) {
    this.nodes = nodes;

    assert nodes.get(0).isStart();
    for (int i = 1; i < nodes.size(); i++) {
      assert !nodes.get(i).isStart();
    }
  }

  public GraphTester endNodes(Integer i, Integer... is) {
    Set<Integer> set = new HashSet<>(Arrays.asList(is));
    set.add(i);

    for (int j = 0; j < nodes.size(); j++) {
      assert set.contains(j) == nodes.get(j).isEnd();
    }
    return this;
  }

  public GraphTester edge(int from, int to, String label, String... labels) {
    Set<String> set1 = new HashSet<>();
    GraphNode node = nodes.get(from);
    for (GraphEdge edge : node.edges()) {
      if (nodes.indexOf(edge.destination()) == to) {
        set1.add(edge.label().raw().toString());
      }
    }

    Set<String> set2 = new HashSet<>(Arrays.asList(labels));
    set2.add(label);
    assert set1.equals(set2);
    return this;
  }

  public GraphTester noEdge(Integer i, Integer... is) {
    List<Integer> ls = new ArrayList<>(Arrays.asList(is));
    ls.add(i);
    for (Integer e : ls) {
      assert nodes.get(e).edges().size() == 0;
    }
    return this;
  }

  public GraphTester tags(int i, String tag, String... tags) {
    Set<String> set1 =
        nodes.get(i).tags().stream().map(t -> t.raw().toString()).collect(Collectors.toSet());
    Set<String> set2 = new HashSet<>(Arrays.asList(tags));
    set2.add(tag);
    assert set1.equals(set2);
    return this;
  }

  public GraphTester noTags(Integer i, Integer... is) {
    List<Integer> ls = new ArrayList<>(Arrays.asList(is));
    ls.add(i);
    for (Integer e : ls) {
      assert nodes.get(e).tags().size() == 0;
    }
    return this;
  }
}
