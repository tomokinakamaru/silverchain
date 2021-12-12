package silverchain.internal.middleware.java;

import java.util.function.Consumer;
import silverchain.SilverchainWarning;
import silverchain.internal.middleware.graph.data.graph.collection.Graphs;
import silverchain.internal.middleware.java.builder.GraphTranslator;
import silverchain.internal.middleware.java.data.CompilationUnits;
import silverchain.internal.middleware.java.rewriter.ActionInterfaceGenerator;
import silverchain.internal.middleware.java.rewriter.JavadocProcessor;
import silverchain.internal.middleware.java.rewriter.NodeClassGenerator;

public class JavaMiddleware {

  private String javadoc;

  private Consumer<SilverchainWarning> warningHandler;

  public JavaMiddleware(String javadoc, Consumer<SilverchainWarning> warningHandler) {}

  public CompilationUnits run(Graphs graphs) {
    CompilationUnits stateInterfaces = new GraphTranslator().translate(graphs);
    new JavadocProcessor(javadoc, warningHandler).process(stateInterfaces);
    CompilationUnits actionInterface = new ActionInterfaceGenerator().generate(stateInterfaces);
    CompilationUnits stateClasses = new NodeClassGenerator().generate(stateInterfaces);
    return stateInterfaces;
  }
}
