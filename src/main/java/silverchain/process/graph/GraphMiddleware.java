package silverchain.process.graph;

import java.util.stream.Collectors;
import org.apiguardian.api.API;
import silverchain.data.graph.Graphs;
import silverchain.data.graph.visitor.GraphWalker;
import silverchain.process.ag.antlr.AgParser.InputContext;
import silverchain.process.graph.builder.GraphBuilder;
import silverchain.process.graph.checker.EdgeConflictValidator;
import silverchain.process.graph.rewriter.GraphDeterminizer;
import silverchain.process.graph.rewriter.GraphReverser;
import silverchain.process.graph.rewriter.ParamPropagator;
import silverchain.process.graph.rewriter.ParamRefResolver;

@API(status = API.Status.INTERNAL)
public class GraphMiddleware {

  public Graphs run(InputContext ctx) {
    Graphs graphs = build(ctx);
    GraphWalker walker = new GraphWalker();
    walker.walk(new GraphReverser(), graphs);
    walker.walk(new GraphDeterminizer(), graphs);
    walker.walk(new GraphReverser(), graphs);
    walker.walk(new GraphDeterminizer(), graphs);
    walker.walk(new ParamRefResolver(), graphs);
    walker.walk(new ParamPropagator(), graphs);
    walker.walk(new EdgeConflictValidator(), graphs);
    return graphs;
  }

  protected Graphs build(InputContext ctx) {
    GraphBuilder builder = new GraphBuilder();
    return ctx.typeDecl().stream()
        .map(d -> d.accept(builder))
        .collect(Collectors.toCollection(Graphs::new));
  }
}
