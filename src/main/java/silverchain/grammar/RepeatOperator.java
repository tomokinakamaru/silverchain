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
    return max() == null ? "{" + min() + "}" : "{" + min() + "," + max() + "}";
  }
}
