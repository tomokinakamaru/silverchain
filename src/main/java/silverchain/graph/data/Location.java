package silverchain.graph.data;

import java.util.Objects;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class Location {

  private int line;

  private int column;

  public int line() {
    return line;
  }

  public void line(int line) {
    this.line = line;
  }

  public int column() {
    return column;
  }

  public void column(int column) {
    this.column = column;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Location location = (Location) o;
    return line == location.line && column == location.column;
  }

  @Override
  public int hashCode() {
    return Objects.hash(line, column);
  }
}
