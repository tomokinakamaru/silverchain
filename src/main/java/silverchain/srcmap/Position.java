package silverchain.srcmap;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class Position {

  private int line;

  private int column;

  public Position(int line, int column) {
    line(line);
    column(column);
  }

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
  public String toString() {
    return "L" + line + "C" + column;
  }
}
