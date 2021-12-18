package silverchain.internal.frontend.utility;

import java.util.function.BiFunction;
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
import silverchain.internal.frontend.antlr.AgParser.ChainStmtsContext;
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
import silverchain.internal.frontend.antlr.AgParser.TypeDeclContext;
import silverchain.internal.frontend.antlr.AgParser.TypeParamContext;
import silverchain.internal.frontend.antlr.AgParser.TypeParamsContext;
import silverchain.internal.frontend.antlr.AgParser.TypeRefContext;
import silverchain.internal.frontend.antlr.AgParser.WildcardContext;
import silverchain.internal.frontend.antlr.AgVisitor;

@API(status = API.Status.INTERNAL)
public abstract class ContextBuilder implements AgVisitor<ParseTree> {

  @Override
  public ParseTree visitInput(InputContext ctx) {
    return build(ctx, InputContext::new);
  }

  @Override
  public ParseTree visitImportDecl(ImportDeclContext ctx) {
    return build(ctx, ImportDeclContext::new);
  }

  @Override
  public ParseTree visitFragmentDecl(FragmentDeclContext ctx) {
    return build(ctx, FragmentDeclContext::new);
  }

  @Override
  public ParseTree visitTypeDecl(TypeDeclContext ctx) {
    return build(ctx, TypeDeclContext::new);
  }

  @Override
  public ParseTree visitExternalParams(ExternalParamsContext ctx) {
    return build(ctx, ExternalParamsContext::new);
  }

  @Override
  public ParseTree visitInternalParams(InternalParamsContext ctx) {
    return build(ctx, InternalParamsContext::new);
  }

  @Override
  public ParseTree visitChainStmts(ChainStmtsContext ctx) {
    return build(ctx, ChainStmtsContext::new);
  }

  @Override
  public ParseTree visitChainStmt(ChainStmtContext ctx) {
    return build(ctx, ChainStmtContext::new);
  }

  @Override
  public ParseTree visitChainExpr(ChainExprContext ctx) {
    return build(ctx, ChainExprContext::new);
  }

  @Override
  public ParseTree visitChainTerm(ChainTermContext ctx) {
    return build(ctx, ChainTermContext::new);
  }

  @Override
  public ParseTree visitChainFact(ChainFactContext ctx) {
    return build(ctx, ChainFactContext::new);
  }

  @Override
  public ParseTree visitChainElem(ChainElemContext ctx) {
    return build(ctx, ChainElemContext::new);
  }

  @Override
  public ParseTree visitReturnType(ReturnTypeContext ctx) {
    return build(ctx, ReturnTypeContext::new);
  }

  @Override
  public ParseTree visitRepeatN(RepeatNContext ctx) {
    return build(ctx, RepeatNContext::new);
  }

  @Override
  public ParseTree visitRepeatNX(RepeatNXContext ctx) {
    return build(ctx, RepeatNXContext::new);
  }

  @Override
  public ParseTree visitRepeatNM(RepeatNMContext ctx) {
    return build(ctx, RepeatNMContext::new);
  }

  @Override
  public ParseTree visitPermutation(PermutationContext ctx) {
    return build(ctx, PermutationContext::new);
  }

  @Override
  public ParseTree visitMethod(MethodContext ctx) {
    return build(ctx, MethodContext::new);
  }

  @Override
  public ParseTree visitExceptions(ExceptionsContext ctx) {
    return build(ctx, ExceptionsContext::new);
  }

  @Override
  public ParseTree visitParams(ParamsContext ctx) {
    return build(ctx, ParamsContext::new);
  }

  @Override
  public ParseTree visitParam(ParamContext ctx) {
    return build(ctx, ParamContext::new);
  }

  @Override
  public ParseTree visitFragmentRef(FragmentRefContext ctx) {
    return build(ctx, FragmentRefContext::new);
  }

  @Override
  public ParseTree visitTypeRef(TypeRefContext ctx) {
    return build(ctx, TypeRefContext::new);
  }

  @Override
  public ParseTree visitTypeArgs(TypeArgsContext ctx) {
    return build(ctx, TypeArgsContext::new);
  }

  @Override
  public ParseTree visitTypeArg(TypeArgContext ctx) {
    return build(ctx, TypeArgContext::new);
  }

  @Override
  public ParseTree visitWildcard(WildcardContext ctx) {
    return build(ctx, WildcardContext::new);
  }

  @Override
  public ParseTree visitTypeParams(TypeParamsContext ctx) {
    return build(ctx, TypeParamsContext::new);
  }

  @Override
  public ParseTree visitTypeParam(TypeParamContext ctx) {
    return build(ctx, TypeParamContext::new);
  }

  @Override
  public ParseTree visitBounds(BoundsContext ctx) {
    return build(ctx, BoundsContext::new);
  }

  @Override
  public ParseTree visitName(NameContext ctx) {
    return build(ctx, NameContext::new);
  }

  @Override
  public ParseTree visitQualifier(QualifierContext ctx) {
    return build(ctx, QualifierContext::new);
  }

  @Override
  public ParseTree visit(ParseTree tree) {
    throw new RuntimeException();
  }

  @Override
  public ParseTree visitChildren(RuleNode node) {
    throw new RuntimeException();
  }

  @Override
  public ParseTree visitTerminal(TerminalNode node) {
    return node;
  }

  @Override
  public ParseTree visitErrorNode(ErrorNode node) {
    throw new RuntimeException();
  }

  protected abstract ParseTree build(ParserRuleContext ctx, DefaultConstructor constructor);

  @FunctionalInterface
  protected interface DefaultConstructor extends Constructor<ParserRuleContext, Integer> {}

  @FunctionalInterface
  protected interface Constructor<T, S> extends BiFunction<T, S, ParserRuleContext> {}
}
