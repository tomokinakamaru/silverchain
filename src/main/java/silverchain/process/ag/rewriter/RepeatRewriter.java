package silverchain.process.ag.rewriter;

import static java.lang.Integer.parseInt;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;
import static silverchain.process.ag.rewriter.utility.ContextBuilder.fact;
import static silverchain.process.ag.rewriter.utility.TreeReplicator.replicate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import org.apiguardian.api.API;
import silverchain.data.ag.visitor.AgTreeRewriter;
import silverchain.process.ag.antlr.AgLexer;
import silverchain.process.ag.antlr.AgParser.ChainElemContext;
import silverchain.process.ag.antlr.AgParser.ChainFactContext;
import silverchain.process.ag.antlr.AgParser.ChainTermContext;
import silverchain.process.ag.antlr.AgParser.RepeatNContext;
import silverchain.process.ag.antlr.AgParser.RepeatNMContext;
import silverchain.process.ag.antlr.AgParser.RepeatNXContext;

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
