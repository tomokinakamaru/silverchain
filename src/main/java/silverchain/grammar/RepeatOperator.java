package silverchain.grammar;

import java.util.Optional;

public final class RepeatOperator extends ASTNode2<Integer, Integer> {

  public RepeatOperator(Integer min, Integer max) {
    super(min, max);
  }

  public int min() {
    return left() == null ? 0 : left();
  }

  public Optional<Integer> max() {
    return Optional.ofNullable(right());
  }

  @Override
  public String toString() {
    return "{" + min() + max().map(i -> "," + i).orElse("") + "}";
  }
}
