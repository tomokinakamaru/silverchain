package silverchain.internal.frontend.rewriter;

import static java.lang.Integer.parseInt;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;
import static silverchain.internal.frontend.rewriter.utility.ContextBuilder.fact;
import static silverchain.internal.frontend.rewriter.utility.TreeReplicator.replicate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.apiguardian.api.API;
import silverchain.internal.frontend.antlr.AgLexer;
import silverchain.internal.frontend.antlr.AgParser.ChainElemContext;
import silverchain.internal.frontend.antlr.AgParser.ChainFactContext;
import silverchain.internal.frontend.antlr.AgParser.ChainTermContext;
import silverchain.internal.frontend.antlr.AgParser.RepeatNContext;
import silverchain.internal.frontend.antlr.AgParser.RepeatNMContext;
import silverchain.internal.frontend.antlr.AgParser.RepeatNXContext;
import silverchain.internal.frontend.core.AgTreeRewriter;

@API(status = API.Status.INTERNAL)
public class RepeatRewriter extends AgTreeRewriter {

  @Override
  public ParseTree visitChainTerm(ChainTermContext ctx) {
    ChainTermContext c = (ChainTermContext) super.visitChainTerm(ctx);
    List<ParseTree> children = new ArrayList<>();
    for (ChainFactContext f : ctx.chainFact()) build(f).forEach(children::add);
    return build(ChainTermContext::new, c, children);
  }

  public Stream<ChainFactContext> build(ChainFactContext ctx) {
    if (ctx.repeatN() != null) return build(ctx.chainElem(), ctx.repeatN());
    if (ctx.repeatNX() != null) return build(ctx.chainElem(), ctx.repeatNX());
    if (ctx.repeatNM() != null) return build(ctx.chainElem(), ctx.repeatNM());
    return Stream.of(ctx);
  }

  public Stream<ChainFactContext> build(ChainElemContext elem, RepeatNContext repeat) {
    int n = parseInt(repeat.INT().getText());
    return repeat(fact(elem), n);
  }

  protected Stream<ChainFactContext> build(ChainElemContext elem, RepeatNXContext repeat) {
    int n = parseInt(repeat.INT().getText());
    return concat(repeat(fact(elem), n), Stream.of(zeroMore(elem)));
  }

  protected Stream<ChainFactContext> build(ChainElemContext elem, RepeatNMContext repeat) {
    int min = parseInt(repeat.INT(0).getText());
    int max = parseInt(repeat.INT(1).getText());
    return concat(repeat(fact(elem), min), repeat(zeroOne(elem), max - min));
  }

  protected Stream<ChainFactContext> repeat(ChainFactContext ctx, int n) {
    return range(0, n).mapToObj(i -> replicate(ctx));
  }

  protected ChainFactContext zeroMore(ChainElemContext ctx) {
    ChainFactContext c = fact(ctx);
    c.addAnyChild(terminal(AgLexer.ASTERISK));
    return c;
  }

  protected ChainFactContext zeroOne(ChainElemContext ctx) {
    ChainFactContext c = fact(ctx);
    c.addAnyChild(terminal(AgLexer.QUESTION));
    return c;
  }

  protected TerminalNode terminal(int i) {
    String s = AgLexer.VOCABULARY.getLiteralName(i).replace("'", "");
    return new TerminalNodeImpl(new CommonToken(i, s));
  }
}
