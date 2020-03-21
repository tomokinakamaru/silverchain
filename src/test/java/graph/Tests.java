package graph;

import static graph.TestLabel.atom;
import static silverchain.graph.GraphBuilder.join;
import static silverchain.graph.GraphBuilder.merge;
import static silverchain.graph.GraphBuilder.repeat;

import java.util.List;
import org.junit.jupiter.api.Test;
import silverchain.graph.GraphNode;

final class Tests {

  private static final TestOption option = new TestOption();

  @Test
  void testAtom() {
    List<GraphNode> nodes = atom("foo").compile(option);
    assert nodes.size() == 2;
    assert nodes.get(0).isStart();
    assert nodes.get(1).isEnd();
    assert nodes.get(0).edges().get(0).source() == nodes.get(0);
  }

  @Test
  void testRepeat() {
    List<GraphNode> nodes;
    nodes = repeat(atom("foo"), 0, null).compile(option);
    assert nodes.size() == 1;
    assert nodes.get(0).isStart();
    assert nodes.get(0).isEnd();

    nodes = repeat(atom("foo"), 1, null).compile(option);
    assert nodes.size() == 2;
    assert nodes.get(0).isStart();
    assert nodes.get(1).isEnd();

    nodes = repeat(atom("foo"), 1, 3).compile(option);
    assert nodes.size() == 4;
    assert nodes.get(0).isStart();
    assert nodes.get(1).isEnd();
    assert nodes.get(2).isEnd();
    assert nodes.get(3).isEnd();
  }

  @Test
  void testJoin() {
    List<GraphNode> nodes = join(atom("foo"), atom("bar")).compile(option);
    assert nodes.size() == 3;
    assert nodes.get(0).isStart();
    assert nodes.get(2).isEnd();
  }

  @Test
  void testMerge() {
    List<GraphNode> nodes = merge(atom("foo"), atom("bar")).compile(option);
    assert nodes.size() == 2;
    assert nodes.get(0).isStart();
    assert nodes.get(1).isEnd();
  }

  @Test
  void testTagPropagation() {
    List<GraphNode> nodes;
    nodes = repeat(atom("foo", "T"), 0, null).compile(option);
    assert nodes.size() == 2;
    assert nodes.get(0).tags().isEmpty();
    assert nodes.get(1).tags().size() == 1;

    nodes = join(merge(atom("foo", "T"), atom("foo", "S")), atom("bar")).compile(option);
    assert nodes.size() == 5;
    assert nodes.get(0).edges().size() == 2;
  }
}
