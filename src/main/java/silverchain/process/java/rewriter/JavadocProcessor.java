package silverchain.process.java.rewriter;

import java.util.function.Consumer;
import org.apiguardian.api.API;
import silverchain.SilverchainWarning;
import silverchain.data.java.CompilationUnits;

@API(status = API.Status.INTERNAL)
public class JavadocProcessor {

  public JavadocProcessor(String javadoc, Consumer<SilverchainWarning> handler) {}

  public void process(CompilationUnits units) {}
}
