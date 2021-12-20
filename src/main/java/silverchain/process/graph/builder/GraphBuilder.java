package silverchain.process.graph.builder;

import org.apiguardian.api.API;
import silverchain.data.graph.Edge;
import silverchain.data.graph.Graph;
import silverchain.data.graph.Node;
import silverchain.data.graph.Nodes;
import silverchain.data.graph.attribute.Label;
import silverchain.process.ag.antlr.AgBaseVisitor;
import silverchain.process.ag.antlr.AgParser.ChainExprContext;
import silverchain.process.ag.antlr.AgParser.ChainFactContext;
import silverchain.process.ag.antlr.AgParser.ChainStmtContext;
import silverchain.process.ag.antlr.AgParser.ChainTermContext;
import silverchain.process.ag.antlr.AgParser.MethodContext;
import silverchain.process.ag.antlr.AgParser.TypeDeclBodyContext;
import silverchain.process.ag.antlr.AgParser.TypeDeclContext;

@API(status = API.Status.INTERNAL)
public class GraphBuilder extends AgBaseVisitor<Graph> {

  @Override
  public Graph visitTypeDecl(TypeDeclContext ctx) {
    Graph graph = super.visitTypeDecl(ctx);
    graph.typeDeclaration(AttributeBuilder.build(ctx));
    return graph;
  }

  @Override
  public Graph visitTypeDeclBody(TypeDeclBodyContext ctx) {
    return ctx.chainStmt().stream()
        .map(this::visitChainStmt)
        .reduce(GraphBuilder::union)
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public Graph visitChainStmt(ChainStmtContext ctx) {
    Graph graph1 = visitChainExpr(ctx.chainExpr());
    Graph graph2 = atom(AttributeBuilder.build(ctx.returnType()));
    return concat(graph1, graph2);
  }

  @Override
  public Graph visitChainExpr(ChainExprContext ctx) {
    return ctx.chainTerm().stream()
        .map(this::visitChainTerm)
        .reduce(GraphBuilder::union)
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public Graph visitChainTerm(ChainTermContext ctx) {
    return ctx.chainFact().stream()
        .map(this::visitChainFact)
        .reduce(GraphBuilder::concat)
        .orElseThrow(RuntimeException::new);
  }

  @Override
  public Graph visitChainFact(ChainFactContext ctx) {
    Graph graph = super.visitChainFact(ctx);
    if (ctx.PLUS() != null) return oneMore(graph);
    if (ctx.QUESTION() != null) return zeroOne(graph);
    if (ctx.ASTERISK() != null) return zeroMore(graph);
    return graph;
  }

  @Override
  public Graph visitMethod(MethodContext ctx) {
    return atom(AttributeBuilder.build(ctx));
  }

  protected static Graph atom(Label label) {
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

  protected static Graph oneMore(Graph graph) {
    fuse(graph.targets(), graph.sources());
    return graph;
  }

  protected static Graph zeroOne(Graph graph) {
    fuse(graph.sources(), graph.targets());
    return graph;
  }

  protected static Graph zeroMore(Graph graph) {
    return zeroOne(oneMore(graph));
  }

  protected static Graph concat(Graph graph1, Graph graph2) {
    fuse(graph1.targets(), graph2.sources());
    graph1.targets(graph2.targets());
    return graph1;
  }

  protected static Graph union(Graph graph1, Graph graph2) {
    graph1.sources().addAll(graph2.sources());
    graph1.targets().addAll(graph2.targets());
    return graph1;
  }

  protected static void fuse(Nodes sources, Nodes targets) {
    for (Node source : sources) {
      for (Node target : targets) {
        Edge edge = new Edge();
        edge.target(target);
        source.edges().add(edge);
      }
    }
  }
}
