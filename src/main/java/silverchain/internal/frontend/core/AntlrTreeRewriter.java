package silverchain.internal.frontend.core;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgParser.BoundsContext;
import silverchain.internal.frontend.antlr.AgParser.ChainElemContext;
import silverchain.internal.frontend.antlr.AgParser.ChainExprContext;
import silverchain.internal.frontend.antlr.AgParser.ChainFactContext;
import silverchain.internal.frontend.antlr.AgParser.ChainStmtContext;
import silverchain.internal.frontend.antlr.AgParser.ChainTermContext;
import silverchain.internal.frontend.antlr.AgParser.ExceptionsContext;
import silverchain.internal.frontend.antlr.AgParser.ExternalParamsContext;
import silverchain.internal.frontend.antlr.AgParser.FragmentDeclContext;
import silverchain.internal.frontend.antlr.AgParser.FragmentRefContext;
import silverchain.internal.frontend.antlr.AgParser.ImportDeclContext;
import silverchain.internal.frontend.antlr.AgParser.InputContext;
import silverchain.internal.frontend.antlr.AgParser.InternalParamsContext;
import silverchain.internal.frontend.antlr.AgParser.MethodContext;
import silverchain.internal.frontend.antlr.AgParser.NameContext;
import silverchain.internal.frontend.antlr.AgParser.ParamContext;
import silverchain.internal.frontend.antlr.AgParser.ParamsContext;
import silverchain.internal.frontend.antlr.AgParser.PermutationContext;
import silverchain.internal.frontend.antlr.AgParser.QualifierContext;
import silverchain.internal.frontend.antlr.AgParser.RepeatNContext;
import silverchain.internal.frontend.antlr.AgParser.RepeatNMContext;
import silverchain.internal.frontend.antlr.AgParser.RepeatNXContext;
import silverchain.internal.frontend.antlr.AgParser.ReturnTypeContext;
import silverchain.internal.frontend.antlr.AgParser.TypeArgContext;
import silverchain.internal.frontend.antlr.AgParser.TypeArgsContext;
import silverchain.internal.frontend.antlr.AgParser.TypeDeclBodyContext;
import silverchain.internal.frontend.antlr.AgParser.TypeDeclContext;
import silverchain.internal.frontend.antlr.AgParser.TypeParamContext;
import silverchain.internal.frontend.antlr.AgParser.TypeParamsContext;
import silverchain.internal.frontend.antlr.AgParser.TypeRefContext;
import silverchain.internal.frontend.antlr.AgParser.WildcardContext;
import silverchain.internal.frontend.antlr.AgVisitor;

@API(status = API.Status.INTERNAL)
public abstract class AntlrTreeRewriter implements AgVisitor<ParseTree> {

  @Override
  public ParseTree visitInput(InputContext ctx) {
    return build(InputContext::new, ctx);
  }

  @Override
  public ParseTree visitImportDecl(ImportDeclContext ctx) {
    return build(ImportDeclContext::new, ctx);
  }

  @Override
  public ParseTree visitFragmentDecl(FragmentDeclContext ctx) {
    return build(FragmentDeclContext::new, ctx);
  }

  @Override
  public ParseTree visitTypeDecl(TypeDeclContext ctx) {
    return build(TypeDeclContext::new, ctx);
  }

  @Override
  public ParseTree visitExternalParams(ExternalParamsContext ctx) {
    return build(ExternalParamsContext::new, ctx);
  }

  @Override
  public ParseTree visitInternalParams(InternalParamsContext ctx) {
    return build(InternalParamsContext::new, ctx);
  }

  @Override
  public ParseTree visitTypeDeclBody(TypeDeclBodyContext ctx) {
    return build(TypeDeclBodyContext::new, ctx);
  }

  @Override
  public ParseTree visitChainStmt(ChainStmtContext ctx) {
    return build(ChainStmtContext::new, ctx);
  }

  @Override
  public ParseTree visitChainExpr(ChainExprContext ctx) {
    return build(ChainExprContext::new, ctx);
  }

  @Override
  public ParseTree visitChainTerm(ChainTermContext ctx) {
    return build(ChainTermContext::new, ctx);
  }

  @Override
  public ParseTree visitChainFact(ChainFactContext ctx) {
    return build(ChainFactContext::new, ctx);
  }

  @Override
  public ParseTree visitChainElem(ChainElemContext ctx) {
    return build(ChainElemContext::new, ctx);
  }

  @Override
  public ParseTree visitReturnType(ReturnTypeContext ctx) {
    return build(ReturnTypeContext::new, ctx);
  }

  @Override
  public ParseTree visitRepeatN(RepeatNContext ctx) {
    return build(RepeatNContext::new, ctx);
  }

  @Override
  public ParseTree visitRepeatNX(RepeatNXContext ctx) {
    return build(RepeatNXContext::new, ctx);
  }

  @Override
  public ParseTree visitRepeatNM(RepeatNMContext ctx) {
    return build(RepeatNMContext::new, ctx);
  }

  @Override
  public ParseTree visitPermutation(PermutationContext ctx) {
    return build(PermutationContext::new, ctx);
  }

  @Override
  public ParseTree visitMethod(MethodContext ctx) {
    return build(MethodContext::new, ctx);
  }

  @Override
  public ParseTree visitExceptions(ExceptionsContext ctx) {
    return build(ExceptionsContext::new, ctx);
  }

  @Override
  public ParseTree visitParams(ParamsContext ctx) {
    return build(ParamsContext::new, ctx);
  }

  @Override
  public ParseTree visitParam(ParamContext ctx) {
    return build(ParamContext::new, ctx);
  }

  @Override
  public ParseTree visitFragmentRef(FragmentRefContext ctx) {
    return build(FragmentRefContext::new, ctx);
  }

  @Override
  public ParseTree visitTypeRef(TypeRefContext ctx) {
    return build(TypeRefContext::new, ctx);
  }

  @Override
  public ParseTree visitTypeArgs(TypeArgsContext ctx) {
    return build(TypeArgsContext::new, ctx);
  }

  @Override
  public ParseTree visitTypeArg(TypeArgContext ctx) {
    return build(TypeArgContext::new, ctx);
  }

  @Override
  public ParseTree visitWildcard(WildcardContext ctx) {
    return build(WildcardContext::new, ctx);
  }

  @Override
  public ParseTree visitTypeParams(TypeParamsContext ctx) {
    return build(TypeParamsContext::new, ctx);
  }

  @Override
  public ParseTree visitTypeParam(TypeParamContext ctx) {
    return build(TypeParamContext::new, ctx);
  }

  @Override
  public ParseTree visitBounds(BoundsContext ctx) {
    return build(BoundsContext::new, ctx);
  }

  @Override
  public ParseTree visitName(NameContext ctx) {
    return build(NameContext::new, ctx);
  }

  @Override
  public ParseTree visitQualifier(QualifierContext ctx) {
    return build(QualifierContext::new, ctx);
  }

  @Override
  public ParseTree visitTerminal(TerminalNode node) {
    return node;
  }

  @Override
  public ParseTree visit(ParseTree tree) {
    return tree.accept(this);
  }

  @Override
  public ParseTree visitChildren(RuleNode node) {
    throw new RuntimeException();
  }

  @Override
  public ParseTree visitErrorNode(ErrorNode node) {
    throw new RuntimeException();
  }

  protected ParseTree build(TreeConstructor f, ParserRuleContext ctx) {
    List<ParseTree> children = rewriteChildren(ctx);
    if (children.equals(ctx.children)) return ctx;
    return build(f, ctx, children);
  }

  protected ParserRuleContext build(TreeConstructor f, ParserRuleContext ctx, List<ParseTree> ts) {
    ParserRuleContext c = f.apply(ctx.getParent(), ctx.invokingState);
    c.children = ts;
    c.start = ctx.start;
    c.stop = ctx.stop;
    return c;
  }

  protected List<ParseTree> rewriteChildren(ParserRuleContext ctx) {
    List<ParseTree> children = new ArrayList<>();
    for (ParseTree tree : ctx.children) {
      ParseTree t = tree.accept(this);
      if (t != null) children.add(t);
    }
    return children;
  }
}
