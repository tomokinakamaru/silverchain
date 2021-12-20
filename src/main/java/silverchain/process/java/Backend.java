package silverchain.process.java;

import org.apiguardian.api.API;
import silverchain.data.java.CompilationUnits;

@API(status = API.Status.INTERNAL)
public class Backend {

  private int maxFileCount;

  private String output;

  public Backend(int maxFileCount, String output) {
    this.maxFileCount = maxFileCount;
    this.output = output;
  }

  public void run(CompilationUnits units) {}
}
