package analyzer;

import java.util.List;
import org.junit.jupiter.api.Test;
import silverchain.graph.GraphNode;
import utility.ResourceAnalyzer;

final class Tests {

  private static final ResourceAnalyzer analyzer = new ResourceAnalyzer("analyzer");

  @Test
  void test1() {
    List<GraphNode> nodes = test("test1.ag");
    assert nodes.size() == 2;

    assert nodes.get(0).isStart();
    assert !nodes.get(0).isEnd();
    assert nodes.get(0).edges().size() == 1;
    assert nodes.get(0).tags().size() == 0;

    assert !nodes.get(1).isStart();
    assert nodes.get(1).isEnd();
    assert nodes.get(1).edges().size() == 0;
    assert nodes.get(1).tags().size() == 2;
  }

  @Test
  void test2() {
    List<GraphNode> nodes = test("test2.ag");
    assert nodes.size() == 4;

    assert nodes.get(0).isStart();
    assert !nodes.get(0).isEnd();
    assert nodes.get(0).edges().size() == 1;
    assert nodes.get(0).tags().size() == 0;

    assert !nodes.get(1).isStart();
    assert !nodes.get(1).isEnd();
    assert nodes.get(1).edges().size() == 1;
    assert nodes.get(1).tags().size() == 0;

    assert !nodes.get(2).isStart();
    assert !nodes.get(2).isEnd();
    assert nodes.get(2).edges().size() == 1;
    assert nodes.get(2).tags().size() == 0;

    assert !nodes.get(3).isStart();
    assert nodes.get(3).isEnd();
    assert nodes.get(3).edges().size() == 0;
    assert nodes.get(3).tags().size() == 0;
  }

  @Test
  void test3() {
    List<GraphNode> nodes = test("test3.ag");
    assert nodes.size() == 5;

    assert nodes.get(0).isStart();
    assert !nodes.get(0).isEnd();
    assert nodes.get(0).edges().size() == 1;
    assert nodes.get(0).tags().size() == 0;

    assert !nodes.get(1).isStart();
    assert !nodes.get(1).isEnd();
    assert nodes.get(1).edges().size() == 1;
    assert nodes.get(1).tags().size() == 0;

    assert !nodes.get(2).isStart();
    assert !nodes.get(2).isEnd();
    assert nodes.get(2).edges().size() == 1;
    assert nodes.get(2).tags().size() == 0;

    assert !nodes.get(3).isStart();
    assert !nodes.get(3).isEnd();
    assert nodes.get(3).edges().size() == 1;
    assert nodes.get(3).tags().size() == 0;

    assert !nodes.get(4).isStart();
    assert nodes.get(4).isEnd();
    assert nodes.get(4).edges().size() == 0;
    assert nodes.get(4).tags().size() == 0;
  }

  @Test
  void test4() {
    List<GraphNode> nodes = test("test4.ag");
    assert nodes.size() == 4;

    assert nodes.get(0).isStart();
    assert !nodes.get(0).isEnd();
    assert nodes.get(0).edges().size() == 1;
    assert nodes.get(0).tags().size() == 0;

    assert !nodes.get(1).isStart();
    assert !nodes.get(1).isEnd();
    assert nodes.get(1).edges().size() == 2;
    assert nodes.get(1).tags().size() == 0;

    assert !nodes.get(2).isStart();
    assert !nodes.get(2).isEnd();
    assert nodes.get(2).edges().size() == 1;
    assert nodes.get(2).tags().size() == 0;

    assert !nodes.get(3).isStart();
    assert nodes.get(3).isEnd();
    assert nodes.get(3).edges().size() == 0;
    assert nodes.get(3).tags().size() == 0;
  }

  @Test
  void test5() {
    List<GraphNode> nodes = test("test5.ag");
    assert nodes.size() == 4;

    assert nodes.get(0).isStart();
    assert !nodes.get(0).isEnd();
    assert nodes.get(0).edges().size() == 1;
    assert nodes.get(0).tags().size() == 0;

    assert !nodes.get(1).isStart();
    assert !nodes.get(1).isEnd();
    assert nodes.get(1).edges().size() == 2;
    assert nodes.get(1).tags().size() == 0;

    assert !nodes.get(2).isStart();
    assert !nodes.get(2).isEnd();
    assert nodes.get(2).edges().size() == 1;
    assert nodes.get(2).tags().size() == 0;

    assert !nodes.get(3).isStart();
    assert nodes.get(3).isEnd();
    assert nodes.get(3).edges().size() == 0;
    assert nodes.get(3).tags().size() == 0;
  }

  @Test
  void test6() {
    List<GraphNode> nodes = test("test6.ag");
    assert nodes.size() == 6;

    assert nodes.get(0).isStart();
    assert !nodes.get(0).isEnd();
    assert nodes.get(0).edges().size() == 1;
    assert nodes.get(0).tags().size() == 0;

    assert !nodes.get(1).isStart();
    assert !nodes.get(1).isEnd();
    assert nodes.get(1).edges().size() == 3;
    assert nodes.get(1).tags().size() == 0;

    assert !nodes.get(2).isStart();
    assert nodes.get(2).isEnd();
    assert nodes.get(2).edges().size() == 0;
    assert nodes.get(2).tags().size() == 0;

    assert !nodes.get(3).isStart();
    assert !nodes.get(3).isEnd();
    assert nodes.get(3).edges().size() == 2;
    assert nodes.get(3).tags().size() == 0;

    assert !nodes.get(4).isStart();
    assert !nodes.get(4).isEnd();
    assert nodes.get(4).edges().size() == 2;
    assert nodes.get(4).tags().size() == 0;

    assert !nodes.get(5).isStart();
    assert !nodes.get(5).isEnd();
    assert nodes.get(5).edges().size() == 1;
    assert nodes.get(5).tags().size() == 0;
  }

  @Test
  void test7() {
    List<GraphNode> nodes = test("test7.ag");
    assert nodes.size() == 4;

    assert nodes.get(0).isStart();
    assert !nodes.get(0).isEnd();
    assert nodes.get(0).edges().size() == 1;
    assert nodes.get(0).tags().size() == 0;

    assert !nodes.get(1).isStart();
    assert !nodes.get(1).isEnd();
    assert nodes.get(1).edges().size() == 1;
    assert nodes.get(1).tags().size() == 0;

    assert !nodes.get(2).isStart();
    assert !nodes.get(2).isEnd();
    assert nodes.get(2).edges().size() == 1;
    assert nodes.get(2).tags().size() == 1;

    assert !nodes.get(3).isStart();
    assert nodes.get(3).isEnd();
    assert nodes.get(3).edges().size() == 0;
    assert nodes.get(3).tags().size() == 1;
  }

  @Test
  void test8() {
    List<GraphNode> nodes = test("test8.ag");
    assert nodes.size() == 3;

    assert nodes.get(0).isStart();
    assert !nodes.get(0).isEnd();
    assert nodes.get(0).edges().size() == 1;
    assert nodes.get(0).tags().size() == 0;

    assert !nodes.get(1).isStart();
    assert !nodes.get(1).isEnd();
    assert nodes.get(1).edges().size() == 1;
    assert nodes.get(1).tags().size() == 0;

    assert !nodes.get(2).isStart();
    assert nodes.get(2).isEnd();
    assert nodes.get(2).edges().size() == 0;
    assert nodes.get(2).tags().size() == 0;
  }

  private List<GraphNode> test(String fileName) {
    return analyzer.analyze(fileName);
  }
}
