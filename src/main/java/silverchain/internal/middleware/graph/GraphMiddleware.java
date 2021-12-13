package silverchain.internal.middleware.graph;

import silverchain.internal.frontend.parser.antlr.AgParser.InputContext;
import silverchain.internal.middleware.graph.builder.AgCompiler;
import silverchain.internal.middleware.graph.checker.EdgeConflictValidator;
import silverchain.internal.middleware.graph.data.GraphWalker;
import silverchain.internal.middleware.graph.data.graph.collection.Graphs;
import silverchain.internal.middleware.graph.rewriter.GraphDeterminizer;
import silverchain.internal.middleware.graph.rewriter.GraphReverser;
import silverchain.internal.middleware.graph.rewriter.ParamPropagator;
import silverchain.internal.middleware.graph.rewriter.ParamRefResolver;

public class GraphMiddleware {

  public Graphs run(InputContext ctx) {
    Graphs graphs = new AgCompiler().compile(ctx);
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
}
