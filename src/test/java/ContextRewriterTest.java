import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static utility.Functions.parse;
import static utility.Functions.parser;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.ag.MethodContext;
import silverchain.ag.ReturnTypeContext;
import silverchain.ag.antlr.AgBaseListener;
import silverchain.ag.antlr.AgParser.ChainExprContext;
import silverchain.ag.antlr.AgParser.ChainStmtContext;
import silverchain.ag.antlr.AgParser.InputContext;
import silverchain.ag.builder.AgBuilder;
import silverchain.ag.builder.AgParser;
import silverchain.ag.rewriter.AliasResolver;
import silverchain.ag.rewriter.FragmentResolver;
import silverchain.ag.rewriter.PermutationRewriter;
import silverchain.ag.rewriter.RepeatRewriter;

public class ContextRewriterTest {

  @Test
  void testFragmentResolver() {
    InputContext ctx = parse("$FOO = foo(); Foo { Foo $FOO; }");
    ParseTreeWalker.DEFAULT.walk(new FragmentResolver(), ctx);

    AgBaseListener listener =
        new AgBaseListener() {

          private boolean active;

          @Override
          public void enterChainStmt(ChainStmtContext ctx) {
            active = true;
          }

          @Override
          public void exitChainStmt(ChainStmtContext ctx) {
            active = false;
          }

          @Override
          public void enterMethod(silverchain.ag.antlr.AgParser.MethodContext ctx) {
            if (!active) return;
            MethodContext c = (MethodContext) ctx;
            assertThat(c.getText()).isEqualTo("foo()");
            assertThat(c.start.getLine()).isEqualTo(1);
            assertThat(c.start.getCharPositionInLine()).isEqualTo(7);
            assertThat(c.targets()).hasSize(1);
            assertThat(c.targets().iterator().next().getLine()).isEqualTo(1);
            assertThat(c.targets().iterator().next().getCharPositionInLine()).isEqualTo(24);
          }
        };
    ParseTreeWalker.DEFAULT.walk(listener, ctx);
  }

  @Test
  void testImportResolver1() {
    InputContext ctx = parse("import foo.Foo; Foo { Bar bar(); }");
    ParseTreeWalker.DEFAULT.walk(new AliasResolver(), ctx);
    assertThat(ctx.typeDecl(0).name().getText()).isEqualTo("foo.Foo");
  }

  @Test
  void testImportResolver2() {
    InputContext ctx = parse("import foo.Foo; Bar { Foo foo(); }");
    ParseTreeWalker.DEFAULT.walk(new AliasResolver(), ctx);

    ReturnTypeContext c =
        (ReturnTypeContext) ctx.typeDecl(0).typeDeclBody().chainStmt(0).returnType();
    assertThat(c.start.getLine()).isEqualTo(1);
    assertThat(c.start.getCharPositionInLine()).isEqualTo(22);
    assertThat(c.getText()).isEqualTo("foo.Foo");
    assertThat(c.name()).isEqualTo("Foo");
  }

  @Test
  void testImportResolver3() {
    InputContext ctx = parse("import foo.Foo; Bar { bar.Foo foo(); }");
    ParseTreeWalker.DEFAULT.walk(new AliasResolver(), ctx);

    assertThat(ctx.fragmentDecl()).isEmpty();

    ReturnTypeContext c =
        (ReturnTypeContext) ctx.typeDecl(0).typeDeclBody().chainStmt(0).returnType();
    assertThat(c.start.getLine()).isEqualTo(1);
    assertThat(c.start.getCharPositionInLine()).isEqualTo(22);
    assertThat(c.getText()).isEqualTo("bar.Foo");
    assertThat(c.name()).isEqualTo("bar.Foo");
  }

  private static Arguments[] permutationRewriterData() {
    return new Arguments[] {
      Arguments.of(
          "Foo { Foo { foo() }; }",
          "(chainExpr (chainTerm (chainFact (chainElem "
              + "(chainExpr (chainTerm (chainFact (chainElem "
              + "(chainExpr (chainTerm (chainFact (chainElem (method foo ( ))))))))))))))"),
      Arguments.of(
          "Foo { Foo { foo(), bar() }; }",
          "(chainExpr (chainTerm (chainFact (chainElem (chainExpr "
              + "(chainTerm "
              + "(chainFact (chainElem (chainExpr (chainTerm (chainFact (chainElem (method foo ( )))))))) "
              + "(chainFact (chainElem (chainExpr (chainTerm (chainFact (chainElem (method bar ( ))))))))) "
              + "(chainTerm "
              + "(chainFact (chainElem (chainExpr (chainTerm (chainFact (chainElem (method bar ( )))))))) "
              + "(chainFact (chainElem (chainExpr (chainTerm (chainFact (chainElem (method foo ( ))))))))))))))")
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("permutationRewriterData")
  void testPermutationRewriter(String text, String expected) {
    AgParser parser = parser(text);
    InputContext ctx = parser.input();
    ParseTreeWalker.DEFAULT.walk(new AgBuilder(), ctx);
    ParseTreeWalker.DEFAULT.walk(new PermutationRewriter(), ctx);
    ChainExprContext expr = ctx.typeDecl(0).typeDeclBody().chainStmt(0).chainExpr();
    assertThat(expr.toStringTree(parser)).isEqualTo(expected);
  }

  private static Arguments[] repeatRewriterData() {
    return new Arguments[] {
      Arguments.of(
          "Foo { Foo foo()[2]; }",
          "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))) (chainFact (chainElem (method foo ( ))))))"),
      Arguments.of(
          "Foo { Foo foo()[1,]; }",
          "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))) (chainFact (chainElem (method foo ( ))) *)))"),
      Arguments.of(
          "Foo { Foo foo()[1,2]; }",
          "(chainExpr (chainTerm (chainFact (chainElem (method foo ( )))) (chainFact (chainElem (method foo ( ))) ?)))"),
      Arguments.of(
          "Foo { Foo foo()[0,1]; }",
          "(chainExpr (chainTerm (chainFact (chainElem (method foo ( ))) ?)))"),
      Arguments.of(
          "Foo { Foo foo()[1,1]; }",
          "(chainExpr (chainTerm (chainFact (chainElem (method foo ( ))))))"),
      Arguments.of(
          "Foo { Foo foo()*; }",
          "(chainExpr (chainTerm (chainFact (chainElem (method foo ( ))) *)))"),
    };
  }

  @ParameterizedTest(name = "[{index}] \"{0}\" -> \"{1}\"")
  @MethodSource("repeatRewriterData")
  void testRepeatRewriter(String text, String expected) {
    AgParser parser = parser(text);
    InputContext ctx = parser.input();
    ParseTreeWalker.DEFAULT.walk(new AgBuilder(), ctx);
    ParseTreeWalker.DEFAULT.walk(new RepeatRewriter(), ctx);
    ChainExprContext expr = ctx.typeDecl(0).typeDeclBody().chainStmt(0).chainExpr();
    assertThat(expr.toStringTree(parser)).isEqualTo(expected);
  }
}
