package silverchain.graph;

import org.apiguardian.api.API;
import silverchain.ag.data.DeclsTree;
import silverchain.ag.data.TypeDeclTree;
import silverchain.ag.walker.TreeListener;
import silverchain.ag.walker.TreeStack;
import silverchain.ag.walker.TreeWalker;
import silverchain.graph.data.Graphs;

@API(status = API.Status.INTERNAL)
public final class GraphBuilder implements TreeListener<Graphs> {

  private static final GraphBuilder BUILDER = new GraphBuilder();

  private GraphBuilder() {}

  public static Graphs build(DeclsTree tree) {
    Graphs graphs = new Graphs();
    TreeWalker.walk(tree, BUILDER, graphs);
    return graphs;
  }

  @Override
  public void enter(TreeStack ancestors, TypeDeclTree tree, Graphs arg) {
    arg.add(GraphFactory.create(tree));
  }
}
