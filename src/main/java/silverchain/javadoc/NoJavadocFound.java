package silverchain.javadoc;

import silverchain.warning.Warning;

public final class NoJavadocFound extends Warning {

  private final String path;

  NoJavadocFound(String path) {
    this.path = path;
  }

  @Override
  public String toString() {
    return "No javadoc comments were found in " + path;
  }
}
