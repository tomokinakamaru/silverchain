package silverchain.data.graph.location;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class Location {

  private int line;

  private int column;

  private Location target;

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

  public Location target() {
    return target;
  }

  public void target(Location target) {
    this.target = target;
  }
}
