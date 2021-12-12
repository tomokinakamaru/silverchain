package silverchain.internal.front.compiler;

import silverchain.internal.front.parser.antlr.AgBaseVisitor;
import silverchain.internal.front.parser.antlr.AgParser.ChainExprContext;
import silverchain.internal.front.parser.antlr.AgParser.ChainFactContext;
import silverchain.internal.front.parser.antlr.AgParser.ChainStmtContext;
import silverchain.internal.front.parser.antlr.AgParser.ChainStmtsContext;
import silverchain.internal.front.parser.antlr.AgParser.ChainTermContext;
import silverchain.internal.front.parser.antlr.AgParser.MethodContext;
import silverchain.internal.front.parser.antlr.AgParser.TypeDeclContext;
import silverchain.internal.middle.data.attribute.Label;
import silverchain.internal.middle.data.graph.Edge;
import silverchain.internal.middle.data.graph.Graph;
import silverchain.internal.middle.data.graph.Node;
import silverchain.internal.middle.data.graph.collection.Nodes;

public class GraphBuilder extends AgBaseVisitor<Graph> {

  @Override
  public Graph visitTypeDecl(TypeDeclContext ctx) {
    Graph g = super.visitTypeDecl(ctx);
    g.typeDeclaration(AttributeBuilder.build(ctx));
    return g;
  }

  @Override
  public Graph visitChainStmts(ChainStmtsContext ctx) {
    return ctx.chainStmt().stream()
        .map(c -> c.accept(this))
        .reduce(GraphBuilder::union)
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public Graph visitChainStmt(ChainStmtContext ctx) {
    Graph g1 = visitChainExpr(ctx.chainExpr());
    Graph g2 = atom(AttributeBuilder.build(ctx.returnType()));
    return concat(g1, g2);
  }

  @Override
  public Graph visitChainExpr(ChainExprContext ctx) {
    return ctx.chainTerm().stream()
        .map(c -> c.accept(this))
        .reduce(GraphBuilder::union)
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public Graph visitChainTerm(ChainTermContext ctx) {
    return ctx.chainFact().stream()
        .map(c -> c.accept(this))
        .reduce(GraphBuilder::concat)
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public Graph visitChainFact(ChainFactContext ctx) {
    Graph g = visitChainElem(ctx.chainElem());
    if (ctx.repeat().ZERO_MORE != null) return repeat0(g);
    if (ctx.repeat().ONE_MORE != null) return repeat1(g);
    if (ctx.repeat().ZERO_ONE != null) return optional(g);
    return g;
  }

  @Override
  public Graph visitMethod(MethodContext ctx) {
    return atom(AttributeBuilder.build(ctx));
  }

  public static Graph atom(Label label) {
    Graph graph = new Graph();
    Node source = new Node();
    Node target = new Node();
    Edge edge = new Edge();
    graph.sources().add(source);
    graph.targets().add(target);
    source.edges().add(edge);
    edge.target(target);
    edge.label(label);
    return graph;
  }

  public static Graph repeat0(Graph graph) {
    return optional(repeat1(graph));
  }

  public static Graph repeat1(Graph graph) {
    fuse(graph.targets(), graph.sources());
    return graph;
  }

  public static Graph optional(Graph graph) {
    fuse(graph.sources(), graph.targets());
    return graph;
  }

  public static Graph concat(Graph graph1, Graph graph2) {
    fuse(graph1.targets(), graph2.sources());
    graph1.targets(graph2.targets());
    return graph1;
  }

  public static Graph union(Graph graph1, Graph graph2) {
    graph1.sources().addAll(graph2.sources());
    graph1.targets().addAll(graph2.targets());
    return graph1;
  }

  private static void fuse(Nodes sources, Nodes targets) {
    for (Node source : sources) {
      for (Node target : targets) {
        Edge edge = new Edge();
        edge.target(target);
        source.edges().add(edge);
      }
    }
  }
}
