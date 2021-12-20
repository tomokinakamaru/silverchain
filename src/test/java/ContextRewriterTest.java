import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static utility.Functions.parse;
import static utility.Functions.parser;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import silverchain.data.ag.MethodContext;
import silverchain.data.ag.ReturnTypeContext;
import silverchain.process.ag.antlr.AgBaseListener;
import silverchain.process.ag.antlr.AgParser;
import silverchain.process.ag.antlr.AgParser.ChainExprContext;
import silverchain.process.ag.antlr.AgParser.InputContext;
import silverchain.process.ag.builder.AgTreeBuilder;
import silverchain.process.ag.builder.AntlrParser;
import silverchain.process.ag.rewriter.FragmentResolver;
import silverchain.process.ag.rewriter.ImportResolver;
import silverchain.process.ag.rewriter.PermutationRewriter;
import silverchain.process.ag.rewriter.RepeatRewriter;

public class ContextRewriterTest {

  @Test
  void testFragmentResolver() {
    InputContext ctx = parse("$FOO = foo(); Foo { Foo $FOO; }");
    ctx = (InputContext) ctx.accept(new FragmentResolver());
    assertThat(ctx.fragmentDecl()).isEmpty();

    AgBaseListener listener =
        new AgBaseListener() {
          @Override
          public void enterMethod(AgParser.MethodContext ctx) {
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
    ctx = (InputContext) ctx.accept(new ImportResolver());
    assertThat(ctx.importDecl()).isEmpty();
    assertThat(ctx.typeDecl(0).name().getText()).isEqualTo("foo.Foo");
  }

  @Test
  void testImportResolver2() {
    InputContext ctx = parse("import foo.Foo; Bar { Foo foo(); }");
    ctx = (InputContext) ctx.accept(new ImportResolver());

    assertThat(ctx.importDecl()).isEmpty();

    ReturnTypeContext c =
        (ReturnTypeContext) ctx.typeDecl(0).typeDeclBody().chainStmt(0).returnType();
    assertThat(c.start.getLine()).isEqualTo(1);
    assertThat(c.start.getCharPositionInLine()).isEqualTo(22);
    assertThat(c.getText()).isEqualTo("foo.Foo");
    assertThat(c.sources()).hasSize(1);
    assertThat(c.sources().iterator().next().getLine()).isEqualTo(1);
    assertThat(c.sources().iterator().next().getCharPositionInLine()).isEqualTo(7);
  }

  @Test
  void testImportResolver3() {
    InputContext ctx = parse("import foo.Foo; Bar { bar.Foo foo(); }");
    ctx = (InputContext) ctx.accept(new ImportResolver());

    assertThat(ctx.fragmentDecl()).isEmpty();

    ReturnTypeContext c =
        (ReturnTypeContext) ctx.typeDecl(0).typeDeclBody().chainStmt(0).returnType();
    assertThat(c.start.getLine()).isEqualTo(1);
    assertThat(c.start.getCharPositionInLine()).isEqualTo(22);
    assertThat(c.getText()).isEqualTo("bar.Foo");
    assertThat(c.sources()).isEmpty();
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
    AntlrParser parser = parser(text);
    InputContext ctx = parser.input();
    ctx = (InputContext) ctx.accept(new AgTreeBuilder());
    ctx = (InputContext) ctx.accept(new PermutationRewriter());
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
    AntlrParser parser = parser(text);
    InputContext ctx = parser.input();
    ctx = (InputContext) ctx.accept(new AgTreeBuilder());
    ctx = (InputContext) ctx.accept(new RepeatRewriter());
    ChainExprContext expr = ctx.typeDecl(0).typeDeclBody().chainStmt(0).chainExpr();
    assertThat(expr.toStringTree(parser)).isEqualTo(expected);
  }
}
