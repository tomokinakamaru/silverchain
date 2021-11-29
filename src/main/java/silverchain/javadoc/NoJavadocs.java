package silverchain.javadoc;

import silverchain.Warning;

public final class NoJavadocs extends Warning {

  private final String path;

  NoJavadocs(String path) {
    this.path = path;
  }

  @Override
  public String toString() {
    return "No javadoc comments were found in " + path;
  }
}
