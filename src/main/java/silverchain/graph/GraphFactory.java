package silverchain.graph;

import org.apiguardian.api.API;
import silverchain.ag.data.ChainExprTree;
import silverchain.ag.data.ChainFactTree;
import silverchain.ag.data.ChainStmtTree;
import silverchain.ag.data.ChainTermTree;
import silverchain.ag.data.MethodTree;
import silverchain.ag.data.TypeDeclBodyTree;
import silverchain.ag.data.TypeDeclTree;
import silverchain.ag.data.TypeRefTree;
import silverchain.graph.data.Graph;

@API(status = API.Status.INTERNAL)
public final class GraphFactory {

  private GraphFactory() {}

  public static Graph create(TypeDeclTree tree) {
    Graph graph = create(tree.body());
    graph.type(AttrBuilder.build(tree));
    return graph;
  }

  public static Graph create(TypeDeclBodyTree tree) {
    return tree.stream()
        .map(GraphFactory::create)
        .reduce(GraphEditor::union)
        .orElseThrow(RuntimeException::new);
  }

  public static Graph create(ChainStmtTree tree) {
    Graph graph1 = create(tree.expr());
    Graph graph2 = create(tree.retType());
    return GraphEditor.concat(graph1, graph2);
  }

  public static Graph create(ChainExprTree tree) {
    return tree.stream()
        .map(GraphFactory::create)
        .reduce(GraphEditor::union)
        .orElseThrow(RuntimeException::new);
  }

  public static Graph create(ChainTermTree tree) {
    return tree.stream()
        .map(GraphFactory::create)
        .reduce(GraphEditor::concat)
        .orElseThrow(RuntimeException::new);
  }

  public static Graph create(ChainFactTree tree) {
    Graph graph = create((MethodTree) tree.elem());
    if (tree.quantifier().isRepeat1X()) return GraphEditor.oneMore(graph);
    if (tree.quantifier().isRepeat01()) return GraphEditor.zeroOne(graph);
    if (tree.quantifier().isRepeat0X()) return GraphEditor.zeroMore(graph);
    return graph;
  }

  public static Graph create(TypeRefTree tree) {
    return GraphEditor.atom(AttrBuilder.buildRetType(tree));
  }

  public static Graph create(MethodTree tree) {
    return GraphEditor.atom(AttrBuilder.build(tree));
  }
}
