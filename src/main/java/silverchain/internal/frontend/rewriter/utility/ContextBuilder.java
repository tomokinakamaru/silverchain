package silverchain.internal.frontend.rewriter.utility;

import java.util.function.BiFunction;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apiguardian.api.API;
import silverchain.internal.frontend.parser.antlr.AgParser;
import silverchain.internal.frontend.parser.antlr.AgVisitor;

@API(status = API.Status.INTERNAL)
public abstract class ContextBuilder implements AgVisitor<ParseTree> {

  @Override
  public ParseTree visitInput(AgParser.InputContext ctx) {
    return build(ctx, AgParser.InputContext::new);
  }

  @Override
  public ParseTree visitImportDecl(AgParser.ImportDeclContext ctx) {
    return build(ctx, AgParser.ImportDeclContext::new);
  }

  @Override
  public ParseTree visitFragmentDecl(AgParser.FragmentDeclContext ctx) {
    return build(ctx, AgParser.FragmentDeclContext::new);
  }

  @Override
  public ParseTree visitTypeDecl(AgParser.TypeDeclContext ctx) {
    return build(ctx, AgParser.TypeDeclContext::new);
  }

  @Override
  public ParseTree visitChainStmts(AgParser.ChainStmtsContext ctx) {
    return build(ctx, AgParser.ChainStmtsContext::new);
  }

  @Override
  public ParseTree visitChainStmt(AgParser.ChainStmtContext ctx) {
    return build(ctx, AgParser.ChainStmtContext::new);
  }

  @Override
  public ParseTree visitChainExpr(AgParser.ChainExprContext ctx) {
    return build(ctx, AgParser.ChainExprContext::new);
  }

  @Override
  public ParseTree visitChainTerm(AgParser.ChainTermContext ctx) {
    return build(ctx, AgParser.ChainTermContext::new);
  }

  @Override
  public ParseTree visitChainFact(AgParser.ChainFactContext ctx) {
    return build(ctx, AgParser.ChainFactContext::new);
  }

  @Override
  public ParseTree visitChainElem(AgParser.ChainElemContext ctx) {
    return build(ctx, AgParser.ChainElemContext::new);
  }

  @Override
  public ParseTree visitReturnType(AgParser.ReturnTypeContext ctx) {
    return build(ctx, AgParser.ReturnTypeContext::new);
  }

  @Override
  public ParseTree visitRepeat(AgParser.RepeatContext ctx) {
    return build(ctx, AgParser.RepeatContext::new);
  }

  @Override
  public ParseTree visitPermutation(AgParser.PermutationContext ctx) {
    return build(ctx, AgParser.PermutationContext::new);
  }

  @Override
  public ParseTree visitMethod(AgParser.MethodContext ctx) {
    return build(ctx, AgParser.MethodContext::new);
  }

  @Override
  public ParseTree visitExceptions(AgParser.ExceptionsContext ctx) {
    return build(ctx, AgParser.ExceptionsContext::new);
  }

  @Override
  public ParseTree visitParams(AgParser.ParamsContext ctx) {
    return build(ctx, AgParser.ParamsContext::new);
  }

  @Override
  public ParseTree visitParam(AgParser.ParamContext ctx) {
    return build(ctx, AgParser.ParamContext::new);
  }

  @Override
  public ParseTree visitFragmentRef(AgParser.FragmentRefContext ctx) {
    return build(ctx, AgParser.FragmentRefContext::new);
  }

  @Override
  public ParseTree visitTypeRef(AgParser.TypeRefContext ctx) {
    return build(ctx, AgParser.TypeRefContext::new);
  }

  @Override
  public ParseTree visitTypeArgs(AgParser.TypeArgsContext ctx) {
    return build(ctx, AgParser.TypeArgsContext::new);
  }

  @Override
  public ParseTree visitTypeArg(AgParser.TypeArgContext ctx) {
    return build(ctx, AgParser.TypeArgContext::new);
  }

  @Override
  public ParseTree visitWildcard(AgParser.WildcardContext ctx) {
    return build(ctx, AgParser.WildcardContext::new);
  }

  @Override
  public ParseTree visitTypeParams(AgParser.TypeParamsContext ctx) {
    return build(ctx, AgParser.TypeParamsContext::new);
  }

  @Override
  public ParseTree visitTypeParam(AgParser.TypeParamContext ctx) {
    return build(ctx, AgParser.TypeParamContext::new);
  }

  @Override
  public ParseTree visitBounds(AgParser.BoundsContext ctx) {
    return build(ctx, AgParser.BoundsContext::new);
  }

  @Override
  public ParseTree visitName(AgParser.NameContext ctx) {
    return build(ctx, AgParser.NameContext::new);
  }

  @Override
  public ParseTree visitQualifier(AgParser.QualifierContext ctx) {
    return build(ctx, AgParser.QualifierContext::new);
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
