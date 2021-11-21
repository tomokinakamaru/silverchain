package silverchain.parser;

import java.util.Optional;

public final class RepeatOperator extends ASTNode2<Integer, Integer> {

  public RepeatOperator(Range range, int min, Integer max) {
    super(range, min, max);
  }

  public int min() {
    return left();
  }

  public Optional<Integer> max() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return "[" + min() + max().map(i -> "," + i).orElse(",") + "]";
  }
}
