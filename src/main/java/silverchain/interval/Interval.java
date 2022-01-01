package silverchain.interval;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class Interval {

  private Position begin;

  private Position end;

  public Interval(Position begin, Position end) {
    begin(begin);
    end(end);
  }

  public Position begin() {
    return begin;
  }

  public void begin(Position begin) {
    this.begin = begin;
  }

  public Position end() {
    return end;
  }

  public void end(Position end) {
    this.end = end;
  }

  @Override
  public String toString() {
    return begin + ":" + end;
  }
}
