package silverchain.parser;

import java.util.Objects;

public final class Location implements Comparable<Location> {

  private final int line;

  private final int column;

  public Location(int line, int column) {
    this.line = line;
    this.column = column;
  }

  public int line() {
    return line;
  }

  public int column() {
    return column;
  }

  @Override
  public String toString() {
    return "L" + line + "C" + column;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Location)) {
      return false;
    }
    Location location = (Location) object;
    return line == location.line && column == location.column;
  }

  @Override
  public int hashCode() {
    return Objects.hash(line, column);
  }

  @Override
  public int compareTo(Location location) {
    return line == location.line ? column - location.column : line - location.line;
  }
}
