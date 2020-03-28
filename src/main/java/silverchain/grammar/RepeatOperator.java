package silverchain.grammar;

public final class RepeatOperator extends ASTNode2<Integer, Integer> {

  public RepeatOperator(Integer min, Integer max) {
    super(min, max);
  }

  public Integer min() {
    return left();
  }

  public Integer max() {
    return right();
  }

  @Override
  public String toString() {
    int min = min() == null ? 0 : min();
    if (max() == null) {
      if (min == 0) {
        return "*";
      }
      if (min == 1) {
        return "+";
      }
      return "{" + min() + "}";
    }
    return (min == 0 && max() == 1) ? "?" : "{" + min() + "," + max() + "}";
  }
}
