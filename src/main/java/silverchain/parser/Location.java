package silverchain.parser;

public final class Location {

  private final int line;

  private final int column;

  Location(int line, int column) {
    this.line = line;
    this.column = column;
  }

  public int line() {
    return line;
  }

  public int column() {
    return column;
  }
}
