package silverchain.internal.middleware.graph;

import java.util.stream.Collectors;
import silverchain.internal.frontend.antlr.AgParser.InputContext;
import silverchain.internal.middleware.graph.builder.GraphBuilder;
import silverchain.internal.middleware.graph.checker.EdgeConflictValidator;
import silverchain.internal.middleware.graph.data.GraphWalker;
import silverchain.internal.middleware.graph.data.graph.collection.Graphs;
import silverchain.internal.middleware.graph.rewriter.GraphDeterminizer;
import silverchain.internal.middleware.graph.rewriter.GraphReverser;
import silverchain.internal.middleware.graph.rewriter.ParamPropagator;
import silverchain.internal.middleware.graph.rewriter.ParamRefResolver;

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
