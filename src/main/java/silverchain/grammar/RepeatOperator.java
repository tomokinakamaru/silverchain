package silverchain.grammar;

public final class RepeatOperator extends ASTNode2<Integer, Integer> {

  public RepeatOperator(Integer min, Integer max) {
    super(min, max);
  }

  public int min() {
    return left() == null ? 0 : left();
  }

  public Integer max() {
    return right();
  }

  @Override
  public String toString() {
    if (max() == null) {
      if (min() == 0) {
        return "*";
      }
      if (min() == 1) {
        return "+";
      }
      return "{" + min() + "}";
    }
    return (min() == 0 && max() == 1) ? "?" : "{" + min() + "," + max() + "}";
  }
}
