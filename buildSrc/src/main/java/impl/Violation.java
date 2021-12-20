package impl;

public class Violation {

  private final String path;

  private final String message;

  public Violation(String path, String message) {
    this.path = path;
    this.message = message;
  }

  @Override
  public String toString() {
    return String.format("%s: %s", path, message);
  }
}
