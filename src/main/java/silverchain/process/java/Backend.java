package silverchain.process.java;

import silverchain.data.java.CompilationUnits;

public class Backend {

  private int maxFileCount;

  private String output;

  public Backend(int maxFileCount, String output) {
    this.maxFileCount = maxFileCount;
    this.output = output;
  }

  public void run(CompilationUnits units) {}
}
