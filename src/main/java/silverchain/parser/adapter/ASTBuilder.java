package silverchain.parser.adapter;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import silverchain.parser.*;

public final class ASTBuilder extends AgBaseVisitor<ASTNode> {

  private final Map<String, RuleExpression> fragments = new HashMap<>();

  @Override
  public Input visitInput(AgParser.InputContext ctx) {
    if (ctx.apiDefinition() != null) {
      return visitApiDefinition(ctx.apiDefinition());
    }
    throw new RuntimeException();
  }

  @Override
  public Input visitApiDefinition(AgParser.ApiDefinitionContext ctx) {
    ImportStatements is = null;
    if (ctx.apiDefinitionHead() != null) {
      is = visitApiDefinitionHead(ctx.apiDefinitionHead());
    }
    Grammars gs = visitApiDefinitionBody(ctx.apiDefinitionBody());
    return new Input(range(ctx.start, ctx.stop), is, gs);
  }

  @Override
  public ImportStatements visitApiDefinitionHead(AgParser.ApiDefinitionHeadContext ctx) {
    ImportStatements is = null;
    if (ctx.aliasDeclarations() != null) {
      is = visitAliasDeclarations(ctx.aliasDeclarations());
    }
    return is;
  }

  @Override
  public Grammars visitApiDefinitionBody(AgParser.ApiDefinitionBodyContext ctx) {
    return visitClassDeclarations(ctx.classDeclarations());
  }

  @Override
  public ImportStatements visitAliasDeclarations(AgParser.AliasDeclarationsContext ctx) {
    ImportStatement i = visitAliasDeclaration(ctx.aliasDeclaration());
    ImportStatements is = null;
    if (ctx.aliasDeclarations() != null) {
      is = visitAliasDeclarations(ctx.aliasDeclarations());
    }
    return new ImportStatements(range(ctx.start, ctx.stop), i, is);
  }

  @Override
  public ImportStatement visitAliasDeclaration(AgParser.AliasDeclarationContext ctx) {
    QualifiedName q = visitQualifiedName(ctx.qualifiedName());
    return new ImportStatement(range(ctx.start, ctx.stop), q);
  }

  @Override
  public ASTNode visitFragmentDeclarations(AgParser.FragmentDeclarationsContext ctx) {
    visitFragmentDeclaration(ctx.fragmentDeclaration());
    if (ctx.fragmentDeclarations() != null) {
      visitFragmentDeclarations(ctx.fragmentDeclarations());
    }
    return null;
  }

  @Override
  public ASTNode visitFragmentDeclaration(AgParser.FragmentDeclarationContext ctx) {
    Token t = ctx.FRAGMENT_NAME().getSymbol();
    RuleExpression r = visitRuleExpression(ctx.ruleExpression());
    fragments.put(t.getText(), r);
    return null;
  }

  @Override
  public Grammars visitClassDeclarations(AgParser.ClassDeclarationsContext ctx) {
    Grammar g = visitClassDeclaration(ctx.classDeclaration());
    Grammars gs = null;
    if (ctx.classDeclarations() != null) {
      gs = visitClassDeclarations(ctx.classDeclarations());
    }
    return new Grammars(range(ctx.start, ctx.stop), g, gs);
  }

  @Override
  public Grammar visitClassDeclaration(AgParser.ClassDeclarationContext ctx) {
    if (ctx.fragmentDeclarations() != null) {
      visitFragmentDeclarations(ctx.fragmentDeclarations());
    }

    Type t = visitClassDeclarationHead(ctx.classDeclarationHead());
    Rules rs = null;
    if (ctx.classDeclarationBody() != null) {
      rs = visitClassDeclarationBody(ctx.classDeclarationBody());
    }
    return new Grammar(range(ctx.start, ctx.stop), t, rs);
  }

  @Override
  public Type visitClassDeclarationHead(AgParser.ClassDeclarationHeadContext ctx) {
    QualifiedName n = visitQualifiedName(ctx.qualifiedName());
    TypeParameters ps = null;
    if (ctx.classTypeParameterDeclarations() != null) {
      ps = visitClassTypeParameterDeclarations(ctx.classTypeParameterDeclarations());
    }
    return new Type(range(ctx.start, ctx.stop), n, ps);
  }

  @Override
  public Rules visitClassDeclarationBody(AgParser.ClassDeclarationBodyContext ctx) {
    if (ctx.ruleStatements() != null) {
      return visitRuleStatements(ctx.ruleStatements());
    }
    return null;
  }

  @Override
  public TypeParameters visitClassTypeParameterDeclarations(
      AgParser.ClassTypeParameterDeclarationsContext ctx) {
    TypeParameterList ps1 = null;
    if (ctx.externalTypeParameterDeclarations() != null) {
      ps1 = visitExternalTypeParameterDeclarations(ctx.externalTypeParameterDeclarations());
    }
    TypeParameterList ps2 = null;
    if (ctx.internalTypeParameterDeclarations() != null) {
      ps2 = visitInternalTypeParameterDeclarations(ctx.internalTypeParameterDeclarations());
    }
    return new TypeParameters(range(ctx.start, ctx.stop), ps1, ps2);
  }

  @Override
  public TypeParameterList visitExternalTypeParameterDeclarations(
      AgParser.ExternalTypeParameterDeclarationsContext ctx) {
    return visitTypeParameterList(ctx.typeParameterList());
  }

  @Override
  public TypeParameterList visitInternalTypeParameterDeclarations(
      AgParser.InternalTypeParameterDeclarationsContext ctx) {
    return visitTypeParameterList(ctx.typeParameterList());
  }

  @Override
  public Rules visitRuleStatements(AgParser.RuleStatementsContext ctx) {
    Rule r = visitRuleStatement(ctx.ruleStatement());
    Rules rs = null;
    if (ctx.ruleStatements() != null) {
      rs = visitRuleStatements(ctx.ruleStatements());
    }
    return new Rules(range(ctx.start, ctx.stop), r, rs);
  }

  @Override
  public Rule visitRuleStatement(AgParser.RuleStatementContext ctx) {
    RuleExpression e = visitRuleExpression(ctx.ruleExpression());
    TypeReference r = null;
    if (ctx.typeReference() != null) {
      r = visitTypeReference(ctx.typeReference());
    }
    return new Rule(range(ctx.start, ctx.stop), e, r);
  }

  @Override
  public RuleExpression visitRuleExpression(AgParser.RuleExpressionContext ctx) {
    RuleTerm t = visitRuleTerm(ctx.ruleTerm());
    RuleExpression e = null;
    if (ctx.ruleExpression() != null) {
      e = visitRuleExpression(ctx.ruleExpression());
    }
    return new RuleExpression(range(ctx.start, ctx.stop), t, e);
  }

  @Override
  public RuleTerm visitRuleTerm(AgParser.RuleTermContext ctx) {
    RuleFactor f = visitRuleFactor(ctx.ruleFactor());
    RuleTerm t = null;
    if (ctx.ruleTerm() != null) {
      t = visitRuleTerm(ctx.ruleTerm());
    }
    return new RuleTerm(range(ctx.start, ctx.stop), f, t);
  }

  @Override
  public RuleFactor visitRuleFactor(AgParser.RuleFactorContext ctx) {
    RuleElement e = visitRuleElement(ctx.ruleElement());
    RepeatOperator o = null;
    if (ctx.repeatOperator() != null) {
      o = visitRepeatOperator(ctx.repeatOperator());
    }
    return new RuleFactor(range(ctx.start, ctx.stop), e, o);
  }

  @Override
  public RuleElement visitRuleElement(AgParser.RuleElementContext ctx) {
    if (ctx.ruleAtom() != null) {
      return visitRuleAtom(ctx.ruleAtom());
    }
    if (ctx.ruleUnit() != null) {
      return visitRuleUnit(ctx.ruleUnit());
    }
    throw new RuntimeException();
  }

  @Override
  public RuleElement visitRuleAtom(AgParser.RuleAtomContext ctx) {
    if (ctx.method() != null) {
      Method m = visitMethod(ctx.method());
      return new RuleElement(m.range(), m, null, false);
    }
    if (ctx.fragmentReference() != null) {
      return visitFragmentReference(ctx.fragmentReference());
    }
    throw new RuntimeException();
  }

  @Override
  public RuleElement visitRuleUnit(AgParser.RuleUnitContext ctx) {
    if (ctx.ruleExpression() != null) {
      RuleExpression e = visitRuleExpression(ctx.ruleExpression());
      RuleExpressions r = new RuleExpressions(e.range(), e, null);
      return new RuleElement(range(ctx.start, ctx.stop), null, r, false);
    }
    if (ctx.ruleExpressionList() != null) {
      RuleExpressions rs = visitRuleExpressionList(ctx.ruleExpressionList());
      return new RuleElement(range(ctx.start, ctx.stop), null, rs, true);
    }
    throw new RuntimeException();
  }

  @Override
  public RuleExpressions visitRuleExpressionList(AgParser.RuleExpressionListContext ctx) {
    RuleExpression e = visitRuleExpression(ctx.ruleExpression());
    RuleExpressions es = null;
    if (ctx.ruleExpressionList() != null) {
      es = visitRuleExpressionList(ctx.ruleExpressionList());
    }
    return new RuleExpressions(range(ctx.start, ctx.stop), e, es);
  }

  @Override
  public RepeatOperator visitRepeatOperator(AgParser.RepeatOperatorContext ctx) {
    if (ctx.repeatOperator0X() != null) {
      return visitRepeatOperator0X(ctx.repeatOperator0X());
    }
    if (ctx.repeatOperator1X() != null) {
      return visitRepeatOperator1X(ctx.repeatOperator1X());
    }
    if (ctx.repeatOperator01() != null) {
      return visitRepeatOperator01(ctx.repeatOperator01());
    }
    if (ctx.repeatOperatorNN() != null) {
      return visitRepeatOperatorNN(ctx.repeatOperatorNN());
    }
    if (ctx.repeatOperatorNM() != null) {
      return visitRepeatOperatorNM(ctx.repeatOperatorNM());
    }
    if (ctx.repeatOperatorNX() != null) {
      return visitRepeatOperatorNX(ctx.repeatOperatorNX());
    }
    throw new RuntimeException();
  }

  @Override
  public RepeatOperator visitRepeatOperator01(AgParser.RepeatOperator01Context ctx) {
    return new RepeatOperator(range(ctx.start, ctx.stop), 0, 1);
  }

  @Override
  public RepeatOperator visitRepeatOperator0X(AgParser.RepeatOperator0XContext ctx) {
    return new RepeatOperator(range(ctx.start, ctx.stop), 0, null);
  }

  @Override
  public RepeatOperator visitRepeatOperator1X(AgParser.RepeatOperator1XContext ctx) {
    return new RepeatOperator(range(ctx.start, ctx.stop), 1, null);
  }

  @Override
  public RepeatOperator visitRepeatOperatorNN(AgParser.RepeatOperatorNNContext ctx) {
    int n = Integer.parseInt(ctx.INTEGER().getSymbol().getText());
    return new RepeatOperator(range(ctx.start, ctx.stop), n, n);
  }

  @Override
  public RepeatOperator visitRepeatOperatorNX(AgParser.RepeatOperatorNXContext ctx) {
    int min = Integer.parseInt(ctx.INTEGER().getSymbol().getText());
    return new RepeatOperator(range(ctx.start, ctx.stop), min, null);
  }

  @Override
  public RepeatOperator visitRepeatOperatorNM(AgParser.RepeatOperatorNMContext ctx) {
    int min = Integer.parseInt(ctx.INTEGER(0).getSymbol().getText());
    int max = Integer.parseInt(ctx.INTEGER(1).getSymbol().getText());
    return new RepeatOperator(range(ctx.start, ctx.stop), min, max);
  }

  @Override
  public Method visitMethod(AgParser.MethodContext ctx) {
    TypeParameterList ts = null;
    if (ctx.methodHead().methodTypeParameterDeclarations() != null) {
      ts = visitMethodTypeParameterDeclarations(ctx.methodHead().methodTypeParameterDeclarations());
    }
    FormalParameters fs = visitFormalParameters(ctx.methodTail().formalParameters());
    MethodParameters mps = new MethodParameters(range(ctx.start, ctx.stop), ts, fs);

    Token n = ctx.methodHead().NAME().getSymbol();
    TypeReferences rs = null;
    if (ctx.methodTail().exceptions() != null) {
      rs = visitExceptions(ctx.methodTail().exceptions());
    }
    return new Method(range(n, ctx.stop), n.getText(), mps, rs);
  }

  @Override
  public TypeParameterList visitMethodTypeParameterDeclarations(
      AgParser.MethodTypeParameterDeclarationsContext ctx) {
    return visitTypeParameterList(ctx.typeParameterList());
  }

  @Override
  public FormalParameters visitFormalParameters(AgParser.FormalParametersContext ctx) {
    if (ctx.formalParameterList() != null) {
      return visitFormalParameterList(ctx.formalParameterList());
    }
    return null;
  }

  @Override
  public FormalParameters visitFormalParameterList(AgParser.FormalParameterListContext ctx) {
    FormalParameter p = visitFormalParameter(ctx.formalParameter());
    FormalParameters ps = null;
    if (ctx.formalParameterList() != null) {
      ps = visitFormalParameterList(ctx.formalParameterList());
    }
    return new FormalParameters(range(ctx.start, ctx.stop), p, ps);
  }

  @Override
  public FormalParameter visitFormalParameter(AgParser.FormalParameterContext ctx) {
    TypeReference r = visitTypeReference(ctx.formalParameterType().typeReference());
    Token n = ctx.NAME().getSymbol();
    Token e = ctx.formalParameterType().ELLIPSIS;
    return new FormalParameter(range(ctx.start, ctx.stop), r, n.getText(), e != null);
  }

  @Override
  public TypeReferences visitExceptions(AgParser.ExceptionsContext ctx) {
    return visitExceptionList(ctx.exceptionList());
  }

  @Override
  public TypeReferences visitExceptionList(AgParser.ExceptionListContext ctx) {
    TypeReference r = visitTypeReference(ctx.typeReference());
    TypeReferences rs = null;
    if (ctx.exceptionList() != null) {
      rs = visitExceptionList(ctx.exceptionList());
    }
    return new TypeReferences(range(ctx.start, ctx.stop), r, rs);
  }

  @Override
  public TypeParameterList visitTypeParameterList(AgParser.TypeParameterListContext ctx) {
    TypeParameter p = visitTypeParameter(ctx.typeParameter());
    TypeParameterList ps = null;
    if (ctx.typeParameterList() != null) {
      ps = visitTypeParameterList(ctx.typeParameterList());
    }
    return new TypeParameterList(range(ctx.start, ctx.stop), p, ps);
  }

  @Override
  public TypeParameter visitTypeParameter(AgParser.TypeParameterContext ctx) {
    Token n = ctx.NAME().getSymbol();
    TypeParameterBound b = null;
    if (ctx.typeParameterBounds() != null) {
      b = visitTypeParameterBounds(ctx.typeParameterBounds());
    }
    return new TypeParameter(range(ctx.start, ctx.stop), n.getText(), b);
  }

  @Override
  public TypeParameterBound visitTypeParameterBounds(AgParser.TypeParameterBoundsContext ctx) {
    TypeReference r = visitTypeParameterBoundList(ctx.typeParameterBoundList());
    return new TypeParameterBound(range(ctx.start, ctx.stop), true, r);
  }

  @Override
  public TypeReference visitTypeParameterBoundList(AgParser.TypeParameterBoundListContext ctx) {
    return visitTypeReference(ctx.typeReference());
  }

  @Override
  public TypeReference visitTypeReference(AgParser.TypeReferenceContext ctx) {
    QualifiedName n = visitQualifiedName(ctx.qualifiedName());
    TypeArguments r = null;
    Token t = ctx.array;
    if (ctx.typeArguments() != null) {
      r = visitTypeArguments(ctx.typeArguments());
    }
    return new TypeReference(range(ctx.start, ctx.stop), n, r, t != null);
  }

  @Override
  public TypeArguments visitTypeArguments(AgParser.TypeArgumentsContext ctx) {
    return visitTypeArgumentList(ctx.typeArgumentList());
  }

  @Override
  public TypeArguments visitTypeArgumentList(AgParser.TypeArgumentListContext ctx) {
    TypeArgument a = visitTypeArgument(ctx.typeArgument());
    TypeArguments as = null;
    if (ctx.typeArgumentList() != null) {
      as = visitTypeArgumentList(ctx.typeArgumentList());
    }
    return new TypeArguments(range(ctx.start, ctx.stop), a, as);
  }

  @Override
  public TypeArgument visitTypeArgument(AgParser.TypeArgumentContext ctx) {
    TypeReference r;
    if (ctx.typeReference() != null) {
      r = visitTypeReference(ctx.typeReference());
      return new TypeArgument(range(ctx.start, ctx.stop), r, null);
    }
    if (ctx.wildcard() != null) {
      return visitWildcard(ctx.wildcard());
    }
    throw new RuntimeException();
  }

  @Override
  public TypeArgument visitWildcard(AgParser.WildcardContext ctx) {
    TypeParameterBound b = null;
    if (ctx.wildcardBound() != null) {
      b = visitWildcardBound(ctx.wildcardBound());
    }
    return new TypeArgument(range(ctx.start, ctx.stop), null, b);
  }

  @Override
  public TypeParameterBound visitWildcardBound(AgParser.WildcardBoundContext ctx) {
    Token d = ctx.EXTENDS == null ? ctx.SUPER : ctx.EXTENDS;
    TypeReference r = visitTypeReference(ctx.typeReference());
    boolean u = d.getText().equals("extends");
    return new TypeParameterBound(range(ctx.start, ctx.stop), u, r);
  }

  @Override
  public RuleElement visitFragmentReference(AgParser.FragmentReferenceContext ctx) {
    RuleExpression e = fragments.get(ctx.FRAGMENT_NAME().getText());
    RuleExpressions es = new RuleExpressions(e.range(), e, null);
    return new RuleElement(range(ctx.FRAGMENT_NAME().getSymbol()), null, es, false);
  }

  @Override
  public QualifiedName visitQualifiedName(AgParser.QualifiedNameContext ctx) {
    QualifiedName qualifiedName = null;
    if (ctx.qualifiedName() != null) {
      qualifiedName = visitQualifiedName(ctx.qualifiedName());
    }
    TerminalNode name = ctx.NAME();
    return new QualifiedName(range(ctx.start, ctx.stop), qualifiedName, name.getText());
  }

  private static Range range(Token token1, Token token2) {
    return new Range(range(token1).begin(), range(token2).end());
  }

  private static Range range(Token token) {
    return new Range(
        new Location(token.getLine(), token.getCharPositionInLine() + 1),
        new Location(token.getLine(), token.getCharPositionInLine() + token.getText().length()));
  }
}
