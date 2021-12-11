package silverchain.internal.translator.tree;

import static silverchain.internal.translator.tree.AttrBuilder.build;
import static silverchain.internal.translator.tree.GraphUtils.atom;
import static silverchain.internal.translator.tree.GraphUtils.concat;
import static silverchain.internal.translator.tree.GraphUtils.optional;
import static silverchain.internal.translator.tree.GraphUtils.repeat;
import static silverchain.internal.translator.tree.GraphUtils.union;

import silverchain.internal.front.parser.AgBaseVisitor;
import silverchain.internal.front.parser.AgParser.ChainExprContext;
import silverchain.internal.front.parser.AgParser.ChainFactContext;
import silverchain.internal.front.parser.AgParser.ChainStmtContext;
import silverchain.internal.front.parser.AgParser.ChainStmtsContext;
import silverchain.internal.front.parser.AgParser.ChainTermContext;
import silverchain.internal.front.parser.AgParser.MethodContext;
import silverchain.internal.front.parser.AgParser.TypeDeclContext;
import silverchain.internal.middle.graph.ir.graph.Graph;

public class GraphBuilder extends AgBaseVisitor<Graph> {

  @Override
  public Graph visitTypeDecl(TypeDeclContext ctx) {
    Graph g = super.visitTypeDecl(ctx);
    g.typeDeclaration(build(ctx));
    return g;
  }

  @Override
  public Graph visitChainStmts(ChainStmtsContext ctx) {
    Graph g = super.visitChainStmt(ctx.chainStmt());
    return ctx.chainStmts() == null ? g : union(g, visitChainStmts(ctx.chainStmts()));
  }

  @Override
  public Graph visitChainStmt(ChainStmtContext ctx) {
    Graph g1 = visitChainExpr(ctx.chainExpr());
    Graph g2 = atom(build(ctx.returnType()));
    return concat(g1, g2);
  }

  @Override
  public Graph visitChainExpr(ChainExprContext ctx) {
    Graph g = visitChainTerm(ctx.chainTerm());
    return ctx.chainExpr() == null ? g : union(g, visitChainExpr(ctx.chainExpr()));
  }

  @Override
  public Graph visitChainTerm(ChainTermContext ctx) {
    Graph g = visitChainFact(ctx.chainFact());
    return ctx.chainTerm() == null ? g : concat(g, visitChainTerm(ctx.chainTerm()));
  }

  @Override
  public Graph visitChainFact(ChainFactContext ctx) {
    Graph g = visitChainElem(ctx.chainElem());
    if (ctx.repeat().ZERO_MORE != null) return repeat(g);
    if (ctx.repeat().ZERO_ONE != null) return optional(g);
    return g;
  }

  @Override
  public Graph visitMethod(MethodContext ctx) {
    return atom(build(ctx));
  }
}
