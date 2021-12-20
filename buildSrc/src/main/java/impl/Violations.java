package impl;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Violations extends ArrayList<Violation> {

  @Override
  public String toString() {
    return stream().map(Violation::toString).collect(Collectors.joining("\n"));
  }
}
