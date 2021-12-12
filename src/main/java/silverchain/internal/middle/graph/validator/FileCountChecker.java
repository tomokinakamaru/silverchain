package silverchain.internal.middle.graph.validator;

import silverchain.internal.middle.data.GraphVisitor;
import silverchain.internal.middle.data.attribute.ReturnType;
import silverchain.internal.middle.data.graph.Node;
import silverchain.internal.middle.data.graph.collection.Graphs;

public class FileCountChecker extends GraphVisitor {

  protected final int maxFileCount;

  protected int count;

  public FileCountChecker(int maxFileCount) {
    this.maxFileCount = maxFileCount;
  }

  @Override
  protected void visit(Node node) {
    if (0 == node.edges().size()) return;
    if (node.edges().stream().anyMatch(e -> e.label() instanceof ReturnType)) return;
    count++;
  }

  @Override
  protected void exit(Graphs graphs) {
    if (maxFileCount < count) {
      throw new FileCountError(count);
    }
  }
}
