package silverchain.parser;

import java.util.Objects;

public final class Range implements Comparable<Range> {

  private final Location begin;

  private final Location end;

  public Range(Location begin, Location end) {
    this.begin = begin;
    this.end = end;
  }

  public Location begin() {
    return begin;
  }

  public Location end() {
    return end;
  }

  @Override
  public String toString() {
    return begin.toString() + "-" + end.toString();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Range)) {
      return false;
    }
    Range range = (Range) object;
    return begin.equals(range.begin) && end.equals(range.end);
  }

  @Override
  public int hashCode() {
    return Objects.hash(begin, end);
  }

  @Override
  public int compareTo(Range range) {
    int c = begin.compareTo(range.begin);
    return c == 0 ? end.compareTo(range.end) : c;
  }
}
