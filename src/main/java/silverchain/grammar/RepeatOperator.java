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
    if (min() == null || min() == 0) {
      if (max() == null) {
        return "*";
      }
      if (max() == 1) {
        return "?";
      }
      return "{" + min() + "," + max() + "}";
    }
    if (min() == 1) {
      if (max() == null) {
        return "+";
      }
    }
    if (max() == null) {
      return "{" + min() + "}";
    }
    return "{" + min() + "," + max() + "}";
  }
}
