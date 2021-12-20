package silverchain.process.java;

import java.util.function.Consumer;
import silverchain.SilverchainWarning;
import silverchain.data.graph.Graphs;
import silverchain.data.java.CompilationUnits;
import silverchain.process.java.builder.GraphTranslator;
import silverchain.process.java.rewriter.ActionInterfaceGenerator;
import silverchain.process.java.rewriter.JavadocProcessor;
import silverchain.process.java.rewriter.NodeClassGenerator;

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
