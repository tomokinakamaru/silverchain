package silverchain.process.java.builder;

import org.apiguardian.api.API;
import silverchain.data.java.CompilationUnits;
import silverchain.data.java.JavaFiles;

@API(status = API.Status.INTERNAL)
public class JavaTranslator {

  public JavaFiles translate(CompilationUnits... units) {
    return new JavaFiles();
  }
}
